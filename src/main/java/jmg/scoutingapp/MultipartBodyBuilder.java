package jmg.scoutingapp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MultipartBodyBuilder {

    private final String boundary;
    private final List<byte[]> parts;

    public MultipartBodyBuilder() {
        this.boundary = "----JavaHttpClientBoundary" + System.currentTimeMillis();
        this.parts = new ArrayList<>();
    }

    public String getBoundary() {
        return boundary;
    }

    public MultipartBodyBuilder addText(String name, String value) throws IOException {
        String part = "--" + boundary + "\r\n" +
                "Content-Disposition: form-data; name=\"" + name + "\"\r\n\r\n" +
                value + "\r\n";
        parts.add(part.getBytes());
        return this;
    }

    public MultipartBodyBuilder addFile(String name, Path filePath) throws IOException {
        String mimeType = Files.probeContentType(filePath);
        if (mimeType == null) mimeType = "application/octet-stream";

        String header = "--" + boundary + "\r\n" +
                "Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + filePath.getFileName() + "\"\r\n" +
                "Content-Type: " + mimeType + "\r\n\r\n";

        parts.add(header.getBytes());
        parts.add(Files.readAllBytes(filePath));
        parts.add("\r\n".getBytes());
        return this;
    }

    public byte[] build() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (byte[] part : parts) {
            outputStream.write(part);
        }
        String ending = "--" + boundary + "--\r\n";
        outputStream.write(ending.getBytes());
        return outputStream.toByteArray();
    }
}
