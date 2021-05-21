package endpoints;

public enum UiEndpoints {
    CART("/cart"), FAVICON("/favicon.ico");
    String path;

    UiEndpoints(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String addPath(String additionalPath) {
        return path + additionalPath;
    }
}