package org.trypticon.hex.binary;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Binary which uses a file channel to access the data on demand.
 *
 * @author trejkaz
 */
public class FileChannelBinary extends AbstractBinary implements Binary, Closeable {

    /**
     * The file channel.
     */
    private final FileChannel channel;

    /**
     * The length of the binary.
     */
    private final long length;

    /**
     * Constructs the binary by opening the given file.
     *
     * @param file the file to open.
     * @throws IOException if an error occurs reading from the file.
     */
    public FileChannelBinary(File file) throws IOException {
        channel = FileChannel.open(file.toPath());
        length = channel.size();
    }

    public long length() {
        return length;
    }

    public byte read(long position) {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        try {
            channel.read(buffer, position);
        } catch (IOException e) {
            throw new RuntimeException("Could not read from file", e);
        }
        buffer.flip();
        return buffer.get();
    }

    public void read(long position, ByteBuffer buffer) {
        try {
            while (buffer.hasRemaining()) {
                int read = channel.read(buffer, position);
                position += read;
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read from file", e);
        }
    }
}
