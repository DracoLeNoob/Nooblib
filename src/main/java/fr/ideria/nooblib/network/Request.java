package fr.ideria.nooblib.network;

import fr.ideria.nooblib.data.json.Json;

public class Request {
    private static final String splitter = "<>";

    private final String title;
    private final Json body;

    public Request(String text){
        String[] split = text.split(splitter);
        this.title = split[0];
        this.body = split.length == 2 ? new Json(split[1]) : new Json();
    }

    public Request(String title, Json body){
        this.title = title.replace(splitter, "");
        this.body = body;
    }

    public String getTitle() { return title; }
    public Json getBody() { return body; }
    public String toSend(){ return title + splitter + body; }

    @Override
    public String toString() {
        return "Request{" +
                "title='" + title + '\'' +
                ", body=" + body +
                '}';
    }
}