package com.askimed.nf.test.genomics;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class FastaUtil {

	public static Map<String, String> readAsMap(Path path) throws IOException {

		BufferedReader br = null;
		try {

			br = new BufferedReader(new InputStreamReader(openTxtOrGzipStream(path)));
			return parse(br);

		} finally {
			br.close();
		}

	}

	private static Map<String, String> parse(BufferedReader br) throws IOException {

		Map<String, String> fasta = new HashMap<String, String>();
		String sample = null;
		String line = null;
		while ((line = br.readLine()) != null) {

			String trimmedLine = line.trim();

			// ignore comments
			if (trimmedLine.isEmpty() || trimmedLine.startsWith(";")) {
				continue;
			}

			if (trimmedLine.startsWith(">")) {
				sample = trimmedLine.substring(1).trim();
				if (fasta.containsKey(sample)) {
					throw new IOException("Duplicate sample " + sample + " detected.");
				}
				fasta.put(sample, "");
			} else {
				if (sample == null) {
					throw new IOException("Fasta file is malformed. Starts with sequence.");
				}
				String sequence = fasta.get(sample);
				fasta.put(sample, sequence + trimmedLine);
			}
		}

		return fasta;

	}

	private static DataInputStream openTxtOrGzipStream(Path path) throws IOException {
		FileInputStream inputStream = new FileInputStream(path.toFile());
		InputStream in2 = decompressStream(inputStream);
		return new DataInputStream(in2);
	}

	public static InputStream decompressStream(InputStream input) throws IOException {
		// we need a pushbackstream to look ahead
		PushbackInputStream pb = new PushbackInputStream(input, 2);
		byte[] signature = new byte[2];
		pb.read(signature); // read the signature
		pb.unread(signature); // push back the signature to the stream
		// check if matches standard gzip magic number
		if (signature[0] == (byte) 0x1f && signature[1] == (byte) 0x8b)
			return new GZIPInputStream(pb);
		else
			return pb;
	}

}
