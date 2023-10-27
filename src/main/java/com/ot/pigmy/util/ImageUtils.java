package com.ot.pigmy.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {

	public static byte[] compressImage(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setLevel(Deflater.BEST_COMPRESSION);
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] tmp = new byte[4 * 1024];
		while (!deflater.finished()) {
			int size = deflater.deflate(tmp);
			outputStream.write(tmp, 0, size);
		}
		try {
			outputStream.close();
		} catch (Exception ignored) {
		}
		return outputStream.toByteArray();
	}

	public static byte[] decompressImage(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] tmp = new byte[4 * 1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(tmp);
				outputStream.write(tmp, 0, count);
			}
			outputStream.close();
		} catch (Exception ignored) {
		}
		return outputStream.toByteArray();
	}
	
	public static byte[] decompressExcel(byte[] data) {
	    Inflater inflater = new Inflater();
	    inflater.setInput(data);
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
	    byte[] tmp = new byte[4 * 1024];

	    try {
	        while (!inflater.finished()) {
	            int count = inflater.inflate(tmp);
	            outputStream.write(tmp, 0, count);
	        }
	    } catch (DataFormatException e) {
	        // Handle the exception, log it, or rethrow if needed.
	        e.printStackTrace();
	        throw new RuntimeException("Failed to decompress image data.", e);
	    } finally {
	        inflater.end(); // Close the Inflater.
	        try {
	            outputStream.close(); // Close the ByteArrayOutputStream.
	        } catch (IOException e) {
	            // Handle the exception, log it, or rethrow if needed.
	            e.printStackTrace();
	        }
	    }

	    return outputStream.toByteArray();
	}


}