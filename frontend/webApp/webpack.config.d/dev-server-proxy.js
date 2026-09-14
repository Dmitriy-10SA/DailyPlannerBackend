config.devServer = config.devServer || {};
config.devServer.proxy = [
    {
        context: ["/api"],
        target: "http://localhost:8080",
        changeOrigin: true
    }
];
