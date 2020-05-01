/*
 * BitcoindRpcClient-JSON-RPC-Client License
 *
 * Copyright (c) 2013, Mikhail Yevchenko.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject
 * to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
/*
 * Repackaged with simple additions for easier maven usage by Alessandro Polverini
 */
package com.savl.bitcoin.rpc.client;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.savl.bitcoin.krotjson.Base64Coder;
import com.savl.bitcoin.krotjson.HexCoder;
import com.savl.bitcoin.rpc.client.api.BitcoindRpcClient;
import com.savl.bitcoin.rpc.client.api.Block;
import com.savl.bitcoin.rpc.client.api.BlockChainInfo;
import com.savl.bitcoin.rpc.client.api.DecodedScript;
import com.savl.bitcoin.rpc.client.api.LockedUnspent;
import com.savl.bitcoin.rpc.client.api.MiningInfo;
import com.savl.bitcoin.rpc.client.api.MultiSig;
import com.savl.bitcoin.rpc.client.api.NetTotals;
import com.savl.bitcoin.rpc.client.api.NetworkInfo;
import com.savl.bitcoin.rpc.client.api.NodeInfo;
import com.savl.bitcoin.rpc.client.api.PeerInfoResult;
import com.savl.bitcoin.rpc.client.api.SmartFeeResult;
import com.savl.bitcoin.rpc.client.api.Unspent;
import com.savl.bitcoin.rpc.client.api.WalletInfo;
import com.savl.bitcoin.rpc.client.api.address.AddressBalance;
import com.savl.bitcoin.rpc.client.api.address.AddressInfo;
import com.savl.bitcoin.rpc.client.api.address.AddressUtxo;
import com.savl.bitcoin.rpc.client.api.address.AddressValidationResult;
import com.savl.bitcoin.rpc.client.api.address.ReceivedAddress;
import com.savl.bitcoin.rpc.client.api.tx.RawTransaction;
import com.savl.bitcoin.rpc.client.api.tx.SignedRawTransaction;
import com.savl.bitcoin.rpc.client.api.tx.Transaction;
import com.savl.bitcoin.rpc.client.api.tx.TransactionsSinceBlock;
import com.savl.bitcoin.rpc.client.api.tx.TxInput;
import com.savl.bitcoin.rpc.client.api.tx.TxOut;
import com.savl.bitcoin.rpc.client.api.tx.TxOutSetInfo;
import com.savl.bitcoin.rpc.client.api.tx.TxOutput;
import com.savl.bitcoin.rpc.client.config.RpcClientConfig;
import com.savl.bitcoin.rpc.client.exceptions.BitcoinRPCException;
import com.savl.bitcoin.rpc.client.exceptions.GenericRpcException;
import com.savl.bitcoin.rpc.client.util.ListMapWrapper;
import com.savl.bitcoin.rpc.client.util.SignatureHashType;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Mikhail Yevchenko m.ṥῥẚɱ.ѓѐḿởύḙ at azazar.com Small modifications by
 * Alessandro Polverini polverini at gmail.com
 */
public class BitcoinJSONRPCClient implements BitcoindRpcClient {

    public static final URL DEFAULT_JSONRPC_URL;
    public static final URL DEFAULT_JSONRPC_TESTNET_URL;
    public static final URL DEFAULT_JSONRPC_REGTEST_URL;
    public static final Charset QUERY_CHARSET = Charset.forName("ISO8859-1");
    private static final Logger LOGGER = Logger.getLogger(BitcoindRpcClient.class.getPackage().getName());
    private Gson gson;


    static {
        String user = "user";
        String password = "pass";
        String host = "localhost";
        String port = null;

        try {
            File configFile = null;
            File home = new File(System.getProperty("user.home"));
            String manuallyConfiguredDataFolderPath = RpcClientConfig.get().bitcoinCoreDataFolder();

            if (!StringUtils.isEmpty(manuallyConfiguredDataFolderPath) &&
                    (configFile = new File(manuallyConfiguredDataFolderPath, "bitcoin.conf")
                    ).exists()) {
                // Look for the config file in the configured bitcoin core data folder
                LOGGER.fine("Using configured data dir: " + manuallyConfiguredDataFolderPath);
            } else if ((configFile = new File(home, ".bitcoin" + File.separatorChar +
                    "bitcoin.conf")
            ).exists()) {
                // Look for the config file on the Linux path
            } else if ((configFile = new File(home, "snap" + File.separatorChar +
                    "bitcoin-core" + File.separatorChar +
                    "common" + File.separatorChar +
                    ".bitcoin" + File.separatorChar +
                    "bitcoin.conf")
            ).exists()) {
                // Look for the config file on the Linux path, when bitcoind was installed via snap
                // Path is: ~/snap/bitcoin-core/common/.bitcoin/bitcoin.conf
            } else if ((configFile = new File(home, "AppData" + File.separatorChar +
                    "Roaming" + File.separatorChar +
                    "Bitcoin" + File.separatorChar +
                    "bitcoin.conf")
            ).exists()) {
                // Look for the cofig file on the Windows path
            }

            // If config file is found, attempt to parse its contents
            if (configFile != null && configFile.exists()) {
                LOGGER.fine("Bitcoin configuration file found");

                Properties configProps = new Properties();
                try (FileInputStream i = new FileInputStream(configFile)) {
                    configProps.load(i);
                }

                user = configProps.getProperty("rpcuser", user);
                password = configProps.getProperty("rpcpassword", password);
                host = configProps.getProperty("rpcconnect", host);
                port = configProps.getProperty("rpcport", port);

                // rpcuser and rpcpassword are being phased out of bitcoind
                // bitcoind shows this warning when these configs are used:
                // "Config options rpcuser and rpcpassword will soon be deprecated. Locally-run instances may remove rpcuser to use cookie-based auth, or may be replaced with rpcauth"
                // Two alternatives for authentication are offered:
                // 1) the config rpcauth, which has the format {0}:{1}${2} containing username, salt, password_hmac
                // See https://github.com/bitcoin/bitcoin/tree/master/share/rpcauth
                // However, this only contains a hash of the password
                // This means the password (which is needed in cleartext for authenticaton) cannot be retrieved anymore from bitcoin.conf
                // 2) the .cookie temporary file
                // When bitcoind starts (and rpcuser / rpcauth are not used), it creates a temporary .cookie file
                // This contains a temporary password for the RPC API
                // The .cookie file is automatically deleted when bitcoind is stopped
                // Option 2) seems like the best one to use for this client, so warn user if rpcuser / rpcpassword are still used

                // Show warning if legacy auth mechanism (using rpcuser / rpcpassword) detected
                if (configProps.getProperty("rpcuser") != null || configProps.getProperty("rpcpassword") != null) {
                    LOGGER.warning("Currently relying on rpcuser / rpcpassword for authentication. "
                            + "This will soon be deprecated in bitcoind. "
                            + "To use newer auth mechanism based on a temporary password, remove properties rpcuser / rpcpassword from bitcoin.conf");
                }

                // Also show warning if rpcauth mechanism is detected
                if (configProps.getProperty("rpcauth") != null) {
                    LOGGER.severe("Currently relying on rpcauth mechanism for authentication. "
                            + "This cannot be used by this library, because the password needed for API authentication cannot be retrieved from bitcoin.conf. "
                            + "To use newer auth mechanism based on a temporary password, remove the property rpcauth from bitcoin.conf");
                }

                // Look for .cookie file, which is in a subfolder of the .bitcoin folder
                // Subfolder is one of regtest, testnet3, or mainnet - depending on which mode bitcoind is currently using
                Optional<Path> cookieFile = Files.walk(configFile.getParentFile().toPath())
                        .filter(f -> f.toFile().getName().equals(".cookie"))
                        .findFirst();

                if (cookieFile.isPresent()) {
                    Path cookieFilePath = cookieFile.get();

                    // Format is __cookie__:tempPassword
                    String cookieFileContents = new String(Files.readAllBytes(cookieFilePath));

                    String[] temp = cookieFileContents.split(":");
                    user = temp[0];
                    password = temp[1];
                }
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

        try {
            DEFAULT_JSONRPC_URL = new URL("http://" + user + ':' + password + "@" + host + ":" + (port == null ? "8332" : port) + "/");
            DEFAULT_JSONRPC_TESTNET_URL = new URL("http://" + user + ':' + password + "@" + host + ":" + (port == null ? "18332" : port) + "/");
            DEFAULT_JSONRPC_REGTEST_URL = new URL("http://" + user + ':' + password + "@" + host + ":" + (port == null ? "18443" : port) + "/");
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public final URL rpcURL;
    private final URL noAuthURL;
    private final String authStr;
    private HostnameVerifier hostnameVerifier;
    private SSLSocketFactory sslSocketFactory;

    public BitcoinJSONRPCClient(String rpcUrl) throws MalformedURLException {
        this(new URL(rpcUrl));
    }

    public BitcoinJSONRPCClient(URL rpc) {
        this.gson = new Gson();
        this.rpcURL = rpc;
        try {
            noAuthURL = new URI(rpc.getProtocol(), null, rpc.getHost(), rpc.getPort(), rpc.getPath(), rpc.getQuery(), null).toURL();
        } catch (MalformedURLException | URISyntaxException ex) {
            throw new IllegalArgumentException(rpc.toString(), ex);
        }
        authStr = rpc.getUserInfo() == null ? null : String.valueOf(Base64Coder.encode(rpc.getUserInfo().getBytes(Charset.forName("ISO8859-1"))));
    }

    public BitcoinJSONRPCClient(boolean testNet) {
        this(testNet ? DEFAULT_JSONRPC_TESTNET_URL : DEFAULT_JSONRPC_URL);
    }

    public BitcoinJSONRPCClient() {
        this(DEFAULT_JSONRPC_TESTNET_URL);
    }

    private static byte[] loadStream(InputStream in, boolean close) throws IOException {
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (; ; ) {
            int nr = in.read(buffer);

            if (nr == -1)
                break;
            if (nr == 0)
                throw new IOException("Read timed out");

            o.write(buffer, 0, nr);
        }
        return o.toByteArray();
    }

    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    public void setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
    }

    @SuppressWarnings("serial")
    protected byte[] prepareRequest(final String method, final Object... params) {
        String request = gson.toJson(new LinkedHashMap<String, Object>() {
            {
                put("method", method);
                put("params", params);
                put("id", "1");
            }
        }, Map.class);
        return request.getBytes(QUERY_CHARSET);
    }

    @SuppressWarnings("serial")
    protected byte[] prepareBatchRequest(final String method, final List<BatchParam> paramsList) {
        Type batchRequestType = new TypeToken<ArrayList<Map<String, Object>>>() {
        }.getType();
        String request = gson.toJson(paramsList.stream().map(batchParam -> new LinkedHashMap<String, Object>() {
            {
                put("method", method);
                put("params", batchParam.params);
                put("id", batchParam.id);
            }
        }).collect(Collectors.toList()), batchRequestType);
        return request.getBytes(QUERY_CHARSET);
    }

    @SuppressWarnings("rawtypes")
    public Object loadResponse(InputStream in, Object expectedID, boolean close) throws IOException, GenericRpcException {
        String responseString = new String(loadStream(in, close), QUERY_CHARSET);
        try {
            Map<String, Object> response = gson.fromJson(responseString, Map.class);
            LOGGER.log(Level.FINE, "Bitcoin JSON-RPC response:\n{0}", responseString);
            try {
                return getResponseObject(expectedID, response);
            } catch (ClassCastException ex) {
                throw new BitcoinRPCException("Invalid server response format (data: \"" + responseString + "\")");
            }
        } catch (JsonParseException e) {
            throw new BitcoinRPCException("Node responded with bad JSON: \n" + responseString, e);
        } finally {
            if (close)
                in.close();
        }
    }

    @SuppressWarnings("rawtypes")
    public Object loadBatchResponse(InputStream in, List<BatchParam> batchParams, boolean close) throws IOException, GenericRpcException {
        String responseString = new String(loadStream(in, close), QUERY_CHARSET);
        try {
            List<Map<String, Object>> response = gson.fromJson(responseString, List.class);
            LOGGER.log(Level.FINE, "Bitcoin JSON-RPC response:\n{0}", responseString);
            try {
                return response.stream().map(item -> {
                    try {
                        Object expectedId = batchParams.stream()
                                .filter(batchParam -> batchParam.id.equals(item.get("id")))
                                .findFirst().orElseGet(() -> new BatchParam(null, null)).id;
                        return getResponseObject(expectedId, item);
                    } catch (BitcoinRPCException e) {
                        return e;
                    }
                }).collect(Collectors.toList());
            } catch (ClassCastException ex) {
                throw new BitcoinRPCException("Invalid server response format (data: \"" + responseString + "\")");
            }
        } catch (JsonParseException e) {
            throw new BitcoinRPCException("Node responded with bad JSON:\n" + responseString, e);
        } finally {
            if (close)
                in.close();
        }
    }

    private Object getResponseObject(Object expectedID, Map response) {
        if (!expectedID.equals(response.get("id")))
            throw new BitcoinRPCException("Wrong response ID (expected: " + expectedID + ", response: " + response.get("id") + ")");

        if (response.get("error") != null)
            throw new BitcoinRPCException(new BitcoinRPCError(response));

        return response.get("result");
    }

    public Object query(String method, Object... o) throws GenericRpcException {
        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) noAuthURL.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);

            if (conn instanceof HttpsURLConnection) {
                if (hostnameVerifier != null)
                    ((HttpsURLConnection) conn).setHostnameVerifier(hostnameVerifier);
                if (sslSocketFactory != null)
                    ((HttpsURLConnection) conn).setSSLSocketFactory(sslSocketFactory);
            }

            conn.setRequestProperty("Authorization", "Basic " + authStr);
            byte[] r = prepareRequest(method, o);
            LOGGER.log(Level.FINE, "Bitcoin JSON-RPC request:\n{0}", new String(r, QUERY_CHARSET));
            conn.getOutputStream().write(r);
            conn.getOutputStream().close();
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                InputStream errorStream = conn.getErrorStream();
                throw new BitcoinRPCException(method,
                        Arrays.deepToString(o),
                        responseCode,
                        conn.getResponseMessage(),
                        errorStream == null ? null : new String(loadStream(errorStream, true)));
            }
            return loadResponse(conn.getInputStream(), "1", true);
        } catch (IOException ex) {
            throw new BitcoinRPCException(method, Arrays.deepToString(o), ex);
        }
    }

    public Object batchQuery(String method, List<BatchParam> batchParams) throws GenericRpcException {
        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) noAuthURL.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);

            if (conn instanceof HttpsURLConnection) {
                if (hostnameVerifier != null)
                    ((HttpsURLConnection) conn).setHostnameVerifier(hostnameVerifier);
                if (sslSocketFactory != null)
                    ((HttpsURLConnection) conn).setSSLSocketFactory(sslSocketFactory);
            }

            conn.setRequestProperty("Authorization", "Basic " + authStr);
            byte[] r = prepareBatchRequest(method, batchParams);
            LOGGER.log(Level.FINE, "Bitcoin JSON-RPC request:\n{0}", new String(r, QUERY_CHARSET));
            conn.getOutputStream().write(r);
            conn.getOutputStream().close();
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                InputStream errorStream = conn.getErrorStream();
                throw new BitcoinRPCException(method,
                        batchParams.stream().map(param -> Arrays.deepToString(param.params)).collect(Collectors.joining()),
                        responseCode,
                        conn.getResponseMessage(),
                        errorStream == null ? null : new String(loadStream(errorStream, true)));
            }
            return loadBatchResponse((conn.getInputStream()), batchParams, true);
        } catch (IOException ex) {
            throw new BitcoinRPCException(method, batchParams.stream()
                    .map(param -> Arrays.deepToString(param.params)).collect(Collectors.joining()), ex);
        }
    }

    @Override
    @SuppressWarnings("serial")
    public String createRawTransaction(List<TxInput> inputs, List<TxOutput> outputs) throws GenericRpcException {
        List<Map<String, ?>> pInputs = new ArrayList<>();

        for (final TxInput txInput : inputs) {
            pInputs.add(new LinkedHashMap<String, Object>() {
                {
                    put("txid", txInput.txid());
                    put("vout", txInput.vout());
                }
            });
        }

        Map<String, Object> pOutputs = new LinkedHashMap<>();

        for (TxOutput txOutput : outputs) {
            pOutputs.put(txOutput.address(), txOutput.amount());
            if (txOutput.data() != null) {
                String hex = HexCoder.encode(txOutput.data());
                pOutputs.put("data", hex);
            }
        }

        return (String) query("createrawtransaction", pInputs, pOutputs);
    }

    @Override
    public String dumpPrivKey(String address) throws GenericRpcException {
        return (String) query("dumpprivkey", address);
    }

    @Override
    public String getAccount(String address) throws GenericRpcException {
        return (String) query("getaccount", address);
    }

    @Override
    public String getAccountAddress(String account) throws GenericRpcException {
        return (String) query("getaccountaddress", account);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getAddressesByAccount(String account) throws GenericRpcException {
        return (List<String>) query("getaddressesbyaccount", account);
    }

    @Override
    public BigDecimal getBalance() throws GenericRpcException {
        return (BigDecimal) query("getbalance");
    }

    @Override
    public BigDecimal getBalance(String account) throws GenericRpcException {
        return (BigDecimal) query("getbalance", account);
    }

    @Override
    public BigDecimal getBalance(String account, int minConf) throws GenericRpcException {
        return (BigDecimal) query("getbalance", account, minConf);
    }

    @Override
    @SuppressWarnings("unchecked")
    public SmartFeeResult estimateSmartFee(int blocks) {
        return new SmartFeeResultMapWrapper((Map<String, ?>) query("estimatesmartfee", blocks));
    }

    @Override
    public Block getBlock(int height) throws GenericRpcException {
        String hash = (String) query("getblockhash", height);
        return getBlock(hash);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public Block getBlock(String blockHash) throws GenericRpcException {
        return new BlockMapWrapper(this, (Map<String, ?>) query("getblock", blockHash));
    }

    @Override
    public String getRawBlock(String blockHash) throws GenericRpcException {
        return (String) query("getblock", blockHash, false);
    }

    @Override
    public String getBlockHash(int height) throws GenericRpcException {
        return (String) query("getblockhash", height);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public BlockChainInfo getBlockChainInfo() throws GenericRpcException {
        return new BlockChainInfoMapWrapper((Map<String, ?>) query("getblockchaininfo"));
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public AddressInfo getAddressInfo(String address) throws GenericRpcException {
        return new AddressInfoMapWrapper((Map<String, ?>) query("getaddressinfo", address));
    }

    @Override
    public int getBlockCount() throws GenericRpcException {
        return ((Number) query("getblockcount")).intValue();
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public TxOutSetInfo getTxOutSetInfo() throws GenericRpcException {
        return new TxOutSetInfoWrapper((Map<String, ?>) query("gettxoutsetinfo"));
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public NetworkInfo getNetworkInfo() throws GenericRpcException {
        return new NetworkInfoWrapper((Map<String, ?>) query("getnetworkinfo"));
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public MiningInfo getMiningInfo() throws GenericRpcException {
        return new MiningInfoWrapper((Map<String, ?>) query("getmininginfo"));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NodeInfo> getAddedNodeInfo(boolean dummy, String node) throws GenericRpcException {
        List<Map<String, ?>> list = ((List<Map<String, ?>>) query("getaddednodeinfo", dummy, node));
        List<NodeInfo> nodeInfoList = new LinkedList<NodeInfo>();
        for (Map<String, ?> m : list) {
            NodeInfoWrapper niw = new NodeInfoWrapper(m);
            nodeInfoList.add(niw);
        }
        return nodeInfoList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public MultiSig createMultiSig(int nRequired, List<String> keys) throws GenericRpcException {
        return new MultiSigWrapper((Map<String, ?>) query("createmultisig", nRequired, keys));
    }

    @Override
    @SuppressWarnings("unchecked")
    public WalletInfo getWalletInfo() {
        return new WalletInfoWrapper((Map<String, ?>) query("getwalletinfo"));
    }

    @Override
    public String getNewAddress() throws GenericRpcException {
        return (String) query("getnewaddress");
    }

    @Override
    public String getNewAddress(String account) throws GenericRpcException {
        return (String) query("getnewaddress", account);
    }

    @Override
    public String getNewAddress(String account, String addressType) throws GenericRpcException {
        return (String) query("getnewaddress", account, addressType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getRawMemPool() throws GenericRpcException {
        return (List<String>) query("getrawmempool");
    }

    @Override
    public String getBestBlockHash() throws GenericRpcException {
        return (String) query("getbestblockhash");
    }

    @Override
    public String getRawTransactionHex(String txId) throws GenericRpcException {
        return (String) query("getrawtransaction", txId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public RawTransaction getRawTransaction(String txId) throws GenericRpcException {
        return new RawTransactionWrapper(this, (Map<String, Object>) query("getrawtransaction", txId, 1));
    }

    @SuppressWarnings("unchecked")
    public List<RawTransaction> getRawTransactions(List<String> txIds) throws GenericRpcException {
        List<Object> rawTransactions = (List<Object>) batchQuery("getrawtransaction",
                txIds.stream().map(txId -> new BatchParam(txId, new Object[]{txId, 1}))
                        .collect(Collectors.toList()));

        return rawTransactions.stream().<RawTransaction>map(rawTransaction -> {
            if (rawTransaction instanceof BitcoinRPCException) {
                Map<String, Object> builtErroredTx = new LinkedHashMap<>();
                BitcoinRPCError rpcError = ((BitcoinRPCException) rawTransaction).getRPCError();
                builtErroredTx.put("txid", rpcError.getId());
                builtErroredTx.put("error", rpcError.getMessage());
                return new RawTransactionWrapper(this, builtErroredTx);
            } else {
                return new RawTransactionWrapper(this, (Map<String, ?>) rawTransaction);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public BigDecimal getReceivedByAddress(String address) throws GenericRpcException {
        return (BigDecimal) query("getreceivedbyaddress", address);
    }

    @Override
    public BigDecimal getReceivedByAddress(String address, int minConf) throws GenericRpcException {
        return (BigDecimal) query("getreceivedbyaddress", address, minConf);
    }

    @Override
    public void importPrivKey(String bitcoinPrivKey) throws GenericRpcException {
        query("importprivkey", bitcoinPrivKey);
    }

    @Override
    public void importPrivKey(String bitcoinPrivKey, String label) throws GenericRpcException {
        query("importprivkey", bitcoinPrivKey, label);
    }

    @Override
    public void importPrivKey(String bitcoinPrivKey, String label, boolean rescan) throws GenericRpcException {
        query("importprivkey", bitcoinPrivKey, label, rescan);
    }

    @Override
    public Object importAddress(String address, String label, boolean rescan) throws GenericRpcException {
        query("importaddress", address, label, rescan);
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Number> listAccounts() throws GenericRpcException {
        return (Map<String, Number>) query("listaccounts");
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Number> listAccounts(int minConf) throws GenericRpcException {
        return (Map<String, Number>) query("listaccounts", minConf);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Number> listAccounts(int minConf, boolean watchonly) throws GenericRpcException {
        return (Map<String, Number>) query("listaccounts", minConf, watchonly);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<LockedUnspent> listLockUnspent() {

        return new ListMapWrapper<LockedUnspent>((List<Map<String, ?>>) query("listlockunspent")) {
            protected LockedUnspent wrap(final Map m) {
                return new LockedUnspentWrapper(m);
            }
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ReceivedAddress> listReceivedByAddress() throws GenericRpcException {
        return new ReceivedAddressListWrapper((List<Map<String, ?>>) query("listreceivedbyaddress"));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ReceivedAddress> listReceivedByAddress(int minConf) throws GenericRpcException {
        return new ReceivedAddressListWrapper((List<Map<String, ?>>) query("listreceivedbyaddress", minConf));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ReceivedAddress> listReceivedByAddress(int minConf, boolean includeEmpty) throws GenericRpcException {
        return new ReceivedAddressListWrapper((List<Map<String, ?>>) query("listreceivedbyaddress", minConf, includeEmpty));
    }

    @Override
    @SuppressWarnings("unchecked")
    public TransactionsSinceBlock listSinceBlock() throws GenericRpcException {
        return new TransactionsSinceBlockWrapper(this, (Map<String, ?>) query("listsinceblock"));
    }

    @Override
    @SuppressWarnings("unchecked")
    public TransactionsSinceBlock listSinceBlock(String blockHash) throws GenericRpcException {
        return new TransactionsSinceBlockWrapper(this, (Map<String, ?>) query("listsinceblock", blockHash));
    }

    @Override
    @SuppressWarnings("unchecked")
    public TransactionsSinceBlock listSinceBlock(String blockHash, int targetConfirmations) throws GenericRpcException {
        return new TransactionsSinceBlockWrapper(this, (Map<String, ?>) query("listsinceblock", blockHash, targetConfirmations));
    }

    @Override
    @SuppressWarnings("unchecked")
    public TransactionsSinceBlock listSinceBlock(String blockHash, int targetConfirmations, boolean includeWatchOnly) throws GenericRpcException {
        return new TransactionsSinceBlockWrapper(this, (Map<String, ?>) query("listsinceblock", blockHash, targetConfirmations, includeWatchOnly));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Transaction> listTransactions() throws GenericRpcException {
        return new TransactionListMapWrapper(this, (List<Map<String, ?>>) query("listtransactions"));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Transaction> listTransactions(String account) throws GenericRpcException {
        return new TransactionListMapWrapper(this, (List<Map<String, ?>>) query("listtransactions", account));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Transaction> listTransactions(String account, int count) throws GenericRpcException {
        return new TransactionListMapWrapper(this, (List<Map<String, ?>>) query("listtransactions", account, count));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Transaction> listTransactions(String account, int count, int skip) throws GenericRpcException {
        return new TransactionListMapWrapper(this, (List<Map<String, ?>>) query("listtransactions", account, count, skip));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Unspent> listUnspent() throws GenericRpcException {
        return new UnspentListWrapper((List<Map<String, ?>>) query("listunspent"));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Unspent> listUnspent(int minConf) throws GenericRpcException {
        return new UnspentListWrapper((List<Map<String, ?>>) query("listunspent", minConf));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Unspent> listUnspent(int minConf, int maxConf) throws GenericRpcException {
        return new UnspentListWrapper((List<Map<String, ?>>) query("listunspent", minConf, maxConf));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Unspent> listUnspent(int minConf, int maxConf, String... addresses) throws GenericRpcException {
        return new UnspentListWrapper((List<Map<String, ?>>) query("listunspent", minConf, maxConf, addresses));
    }

    public boolean lockUnspent(boolean unlock, String txid, int vout) throws GenericRpcException {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("txid", txid);
        params.put("vout", vout);
        return (boolean) query("lockunspent", unlock, Arrays.asList(params).toArray());
    }

    @Override
    public boolean move(String fromAccount, String toAddress, BigDecimal amount) throws GenericRpcException {
        return (boolean) query("move", fromAccount, toAddress, amount);
    }

    @Override
    public boolean move(String fromAccount, String toAddress, BigDecimal amount, String comment) throws GenericRpcException {
        return (boolean) query("move", fromAccount, toAddress, amount, 0, comment);
    }

    @Override
    public boolean move(String fromAccount, String toAddress, BigDecimal amount, int minConf) throws GenericRpcException {
        return (boolean) query("move", fromAccount, toAddress, amount, minConf);
    }

    @Override
    public boolean move(String fromAccount, String toAddress, BigDecimal amount, int minConf, String comment) throws GenericRpcException {
        return (boolean) query("move", fromAccount, toAddress, amount, minConf, comment);
    }

    @Override
    public String sendFrom(String fromAccount, String toAddress, BigDecimal amount) throws GenericRpcException {
        return (String) query("sendfrom", fromAccount, toAddress, amount);
    }

    @Override
    public String sendFrom(String fromAccount, String toAddress, BigDecimal amount, int minConf) throws GenericRpcException {
        return (String) query("sendfrom", fromAccount, toAddress, amount, minConf);
    }

    @Override
    public String sendFrom(String fromAccount, String toAddress, BigDecimal amount, int minConf, String comment) throws GenericRpcException {
        return (String) query("sendfrom", fromAccount, toAddress, amount, minConf, comment);
    }

    @Override
    public String sendFrom(String fromAccount, String toAddress, BigDecimal amount, int minConf, String comment, String commentTo) throws GenericRpcException {
        return (String) query("sendfrom", fromAccount, toAddress, amount, minConf, comment, commentTo);
    }

    @Override
    public String sendRawTransaction(String hex) throws GenericRpcException {
        return (String) query("sendrawtransaction", hex);
    }

    @Override
    public String sendToAddress(String toAddress, BigDecimal amount) throws GenericRpcException {
        return (String) query("sendtoaddress", toAddress, amount);
    }

    @Override
    public String sendToAddress(String toAddress, BigDecimal amount, String comment) throws GenericRpcException {
        return (String) query("sendtoaddress", toAddress, amount, comment);
    }

    @Override
    public String sendToAddress(String toAddress, BigDecimal amount, String comment, String commentTo) throws GenericRpcException {
        return (String) query("sendtoaddress", toAddress, amount, comment, commentTo);
    }

    @Deprecated
    public String signRawTransaction(String hex) throws GenericRpcException {
        return signRawTransaction(hex, null, null, "ALL");
    }

    @Override
    @Deprecated
    public String signRawTransaction(String hex, List<? extends TxInput> inputs, List<String> privateKeys) throws GenericRpcException {
        return signRawTransaction(hex, inputs, privateKeys, "ALL");
    }

    @SuppressWarnings({"serial", "unchecked"})
    @Deprecated
    public String signRawTransaction(String hex, List<? extends TxInput> inputs, List<String> privateKeys, String sigHashType) {
        List<Map<String, ?>> pInputs = null;

        if (inputs != null) {
            pInputs = new ArrayList<>();
            for (final TxInput txInput : inputs) {
                pInputs.add(new LinkedHashMap<String, Object>() {
                    {
                        put("txid", txInput.txid());
                        put("vout", txInput.vout());
                        put("scriptPubKey", txInput.scriptPubKey());
                        if (txInput instanceof ExtendedTxInput) {
                            ExtendedTxInput extin = (ExtendedTxInput) txInput;
                            put("redeemScript", extin.redeemScript());
                            put("amount", extin.amount());
                        }
                    }
                });
            }
        }

        Map<String, ?> result = (Map<String, ?>) query("signrawtransaction", hex, pInputs, privateKeys, sigHashType); //if sigHashType is null it will return the default "ALL"
        if ((Boolean) result.get("complete"))
            return (String) result.get("hex");
        else
            throw new GenericRpcException("Incomplete");
    }

    @SuppressWarnings("serial")
    @Override
    public SignedRawTransaction signRawTransactionWithKey(String hex, List<String> privateKeys, List<? extends TxInput> prevTxs, SignatureHashType sigHashType) {
        List<Map<String, ?>> prevTxsJson = null;
        if (prevTxs != null) {
            prevTxsJson = new ArrayList<>();
            for (TxInput txInput : prevTxs) {
                prevTxsJson.add(new LinkedHashMap<String, Object>() {
                    {
                        put("txid", txInput.txid());
                        put("vout", txInput.vout());
                        put("scriptPubKey", txInput.scriptPubKey());
                        put("amount", txInput.amount());

                        if (txInput instanceof ExtendedTxInput) {
                            ExtendedTxInput extIn = (ExtendedTxInput) txInput;
                            put("redeemScript", extIn.redeemScript());
                            put("witnessScript", extIn.witnessScript());
                        }
                    }
                });
            }
        }

        @SuppressWarnings("unchecked")
        Map<String, ?> result = (Map<String, ?>) query("signrawtransactionwithkey",
                hex,
                privateKeys,
                prevTxsJson,
                sigHashType);

        return new SignedRawTransactionWrapper(result);
    }

    @SuppressWarnings("unchecked")
    public RawTransaction decodeRawTransaction(String hex) throws GenericRpcException {
        Map<String, ?> result = (Map<String, ?>) query("decoderawtransaction", hex);
        RawTransaction rawTransaction = new RawTransactionWrapper(this, result);
        return rawTransaction.vOut().get(0).transaction();
    }

    @Override
    @SuppressWarnings("unchecked")
    public AddressValidationResult validateAddress(String address) throws GenericRpcException {
        final Map<String, ?> m = (Map<String, ?>) query("validateaddress", address);
        return new AddressValidationResultWrapper(m);
    }

    @Deprecated
    @Override
    @SuppressWarnings("unchecked")
    public List<String> generate(int numBlocks) throws BitcoinRPCException {
        return (List<String>) query("generate", numBlocks);
    }

    @Deprecated
    @Override
    @SuppressWarnings("unchecked")
    public List<String> generate(int numBlocks, long maxTries) throws BitcoinRPCException {
        return (List<String>) query("generate", numBlocks, maxTries);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> generateToAddress(int numBlocks, String address) throws BitcoinRPCException {
        return (List<String>) query("generatetoaddress", numBlocks, address);
    }

    @Override
    public BigDecimal estimateFee(int nBlocks) throws GenericRpcException {
        return (BigDecimal) query("estimatefee", nBlocks);
    }

    @Override
    public void invalidateBlock(String hash) throws GenericRpcException {
        query("invalidateblock", hash);
    }

    @Override
    public void reconsiderBlock(String hash) throws GenericRpcException {
        query("reconsiderblock", hash);

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PeerInfoResult> getPeerInfo() throws GenericRpcException {
        final List<Map<String, ?>> l = (List<Map<String, ?>>) query("getpeerinfo");
        return new AbstractList<PeerInfoResult>() {

            @Override
            public PeerInfoResult get(int index) {
                return new PeerInfoWrapper(l.get(index));
            }

            @Override
            public int size() {
                return l.size();
            }
        };
    }

    @Override
    public void stop() {
        query("stop");
    }

    @Override
    public String getRawChangeAddress() throws GenericRpcException {
        return (String) query("getrawchangeaddress");
    }

    @Override
    public long getConnectionCount() throws GenericRpcException {
        return (long) query("getconnectioncount");
    }

    @Override
    public BigDecimal getUnconfirmedBalance() throws GenericRpcException {
        return (BigDecimal) query("getunconfirmedbalance");
    }

    @Override
    public BigDecimal getDifficulty() throws GenericRpcException {
        return (BigDecimal) query("getdifficulty");
    }

    @Override
    @SuppressWarnings("unchecked")
    public NetTotals getNetTotals() throws GenericRpcException {
        return new NetTotalsWrapper((Map<String, ?>) query("getnettotals"));
    }

    @Override
    @SuppressWarnings("unchecked")
    public DecodedScript decodeScript(String hex) throws GenericRpcException {
        return new DecodedScriptWrapper((Map<String, ?>) query("decodescript", hex));
    }

    @Override
    public void ping() throws GenericRpcException {
        query("ping");
    }

    @Override
    public BigDecimal getNetworkHashPs() throws GenericRpcException {
        return (BigDecimal) query("getnetworkhashps");
    }

    @Override
    public boolean setTxFee(BigDecimal amount) throws GenericRpcException {
        return (boolean) query("settxfee", amount);
    }

    /**
     * @param node    example: "192.168.0.6:8333"
     * @param command must be either "add", "remove" or "onetry"
     * @throws GenericRpcException if failed
     */
    @Override
    public void addNode(String node, String command) throws GenericRpcException {
        query("addnode", node, command);
    }

    @Override
    public void backupWallet(String destination) throws GenericRpcException {
        query("backupwallet", destination);
    }

    @Override
    public String signMessage(String bitcoinAdress, String message) throws GenericRpcException {
        return (String) query("signmessage", bitcoinAdress, message);
    }

    @Override
    public void dumpWallet(String filename) throws GenericRpcException {
        query("dumpwallet", filename);
    }

    @Override
    public void importWallet(String filename) throws GenericRpcException {
        query("dumpwallet", filename);
    }

    @Override
    public void keyPoolRefill() throws GenericRpcException {
        keyPoolRefill(100); //default is 100 if you don't send anything
    }

    public void keyPoolRefill(long size) throws GenericRpcException {
        query("keypoolrefill", size);
    }

    @Override
    public BigDecimal getReceivedByAccount(String account) throws GenericRpcException {
        return getReceivedByAccount(account, 1);
    }

    public BigDecimal getReceivedByAccount(String account, int minConf) throws GenericRpcException {
        return new BigDecimal((String) query("getreceivedbyaccount", account, minConf));
    }

    @Override
    public void encryptWallet(String passPhrase) throws GenericRpcException {
        query("encryptwallet", passPhrase);
    }

    @Override
    public void walletPassPhrase(String passPhrase, long timeOut) throws GenericRpcException {
        query("walletpassphrase", passPhrase, timeOut);
    }

    @Override
    public boolean verifyMessage(String bitcoinAddress, String signature, String message) throws GenericRpcException {
        return (boolean) query("verifymessage", bitcoinAddress, signature, message);
    }

    @SuppressWarnings("unchecked")
    @Override
    public MultiSig addMultiSigAddress(int nRequired, List<String> keyObject) throws GenericRpcException {
        return new MultiSigWrapper((Map<String, ?>) query("addmultisigaddress", nRequired, keyObject));
    }

    @SuppressWarnings("unchecked")
    @Override
    public MultiSig addMultiSigAddress(int nRequired, List<String> keyObject, String account) throws GenericRpcException {
        return new MultiSigWrapper((Map<String, ?>) query("addmultisigaddress", nRequired, keyObject, account));
    }

    @Override
    public boolean verifyChain() {
        return verifyChain(3, 6); //3 and 6 are the default values
    }

    public boolean verifyChain(int checklevel, int numblocks) {
        return (boolean) query("verifychain", checklevel, numblocks);
    }

    /**
     * Attempts to submit new block to network. The 'jsonparametersobject'
     * parameter is currently ignored, therefore left out.
     *
     * @param hexData hex data
     */
    @Override
    public void submitBlock(String hexData) {
        query("submitblock", hexData);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Transaction getTransaction(String txId) {

        TransactionWrapper tx = new TransactionWrapper(this, (Map<String, ?>) query("gettransaction", txId));

        // [#88] Request for invalid Tx should fail
        // https://github.com/Polve/JavaBitcoindRpcClient/issues/88
        RawTransaction rawTx = tx.raw();
        if (rawTx == null || rawTx.vIn().isEmpty() || rawTx.vOut().isEmpty()) {
            throw new BitcoinRPCException("Invalid Tx: " + txId);
        }

        return tx;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TxOut getTxOut(String txId, long vout) throws GenericRpcException {
        TxOutWrapper txOut = new TxOutWrapper((Map<String, ?>) query("gettxout", txId, vout, true));
        if (txOut.m == null) {
            return null;
        }
        return txOut;
    }

    @SuppressWarnings("unchecked")
    public TxOut getTxOut(String txId, long vout, boolean includemempool) throws GenericRpcException {
        TxOutWrapper txOut = new TxOutWrapper((Map<String, ?>) query("gettxout", txId, vout, includemempool));
        if (txOut.m == null) {
            return null;
        }
        return txOut;
    }

    @SuppressWarnings("unchecked")
    public AddressBalance getAddressBalance(String address) {
        return new AddressBalanceWrapper((Map<String, ?>) query("getaddressbalance", address));
    }

    @SuppressWarnings("unchecked")
    public List<AddressUtxo> getAddressUtxo(String address) {
        return new AddressUtxoListWrapper((List<Map<String, ?>>) query("getaddressutxos", address));
    }

}
