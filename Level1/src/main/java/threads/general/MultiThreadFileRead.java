package threads.general;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MultiThreadFileRead {

    public static void main(String[] args) throws IOException {
        String fileName = "C:\\Users\\sesa513057\\Downloads\\Software-Installers\\Anaconda3-2018.12-Windows-x86_64.exe";
        MultiThreadFileRead mfr = new MultiThreadFileRead(new File(fileName));
        mfr.init();
    }

    private void init() throws IOException {
        //Get file stream handle, check size and distribute indexes
        RandomAccessFile fileStream = this.getFileStream();

        //Create threads and await them to join, either using join or any concurrent classes
        final int CONCURRENT_THREADS = 4;
        long[][] splitIdx = this.getSplitIndexes(fileStream.length(), CONCURRENT_THREADS);

        byte[][] fileBuf = this.readFileConcurrent(fileStream, splitIdx);

        int totalSize = 0;
        for(int i=0; i<fileBuf.length; i++) {
        	totalSize += fileBuf[i].length;
        }
        
        System.out.println("Size of file in bytes: " + totalSize);

        fileStream.close();
        //Combine file data blocks and write out
    }

    private byte[][] readFileConcurrent(RandomAccessFile fileStream, long[][] splitIdx) {
        Object lock = new Object();
        ArrayList<FileReaderThread> jobList = new ArrayList(4);
        Thread[] threads = new Thread[4];

        for(int i=0; i<splitIdx.length; i++) {
            FileReaderThread frt = new FileReaderThread(lock, fileStream, splitIdx[i]);
            jobList.add(frt);
            Thread t = new Thread(frt);
            threads[i] = t;
            t.start();
        }

        for(int i=0; i<splitIdx.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        byte[][] dataSet = new byte[splitIdx.length][];
        for(int i=0; i<splitIdx.length; i++) {
            dataSet[i] = jobList.get(i).getData();
        }

        return dataSet;
    }

    private long[][] getSplitIndexes(long size, int concurrent_threads) {
        long[][] splitIdx = new long[concurrent_threads][1];
        long inc = size/concurrent_threads;
        long remainder = size%concurrent_threads;
        splitIdx[0] = new long[]{0, inc};
        
        System.out.println("Split 0: " + splitIdx[0][0] + ", " + splitIdx[0][1]);

        for(int i=1; i<concurrent_threads; i++) {
            splitIdx[i] = new long[]{splitIdx[i-1][1]+1, splitIdx[i-1][1]+inc};
            System.out.println("Split " + i + ": " + splitIdx[i][0] + ", " + splitIdx[i][1] + 
            		" = " + (splitIdx[i][1] - splitIdx[i][0]));
        }

        System.out.println("Remainder = " + remainder);

        splitIdx[concurrent_threads-1][1] += remainder;

        return splitIdx;
    }

    private RandomAccessFile getFileStream() throws IOException {
        RandomAccessFile fis = new RandomAccessFile(this.inputFile, "r");
        System.out.println("File size: " + fis.length() + " (" + ((fis.length()/1024)/1024) + " MB)");

        return fis;
    }

    private File inputFile;

    public MultiThreadFileRead(File f) throws FileNotFoundException {
        if(f == null || !f.exists())
            throw new FileNotFoundException("File not found");

        this.inputFile = f;
    }

    private class FileReaderThread implements Runnable {

        private RandomAccessFile fileStream;
        private long[] fileSplit;
        private byte[] data;
        private Object lock;

        public FileReaderThread(Object lock, RandomAccessFile in, long[] splits) {
            this.fileStream = in;
            this.fileSplit = splits;
            this.lock = lock;
        }

        public byte[] getData() {
            return this.data;
        }

        @Override
        public void run() {
            int DEFAULT_BUF_LEN = 1024;
            data = new byte[(int)(fileSplit[1] - fileSplit[0])];
            System.out.println("Buffer data size: " + data.length);
            long offset = fileSplit[0];
            int length = DEFAULT_BUF_LEN - 1;

            while (offset < this.fileSplit[1]) {
                if(offset + DEFAULT_BUF_LEN > this.fileSplit[1]) {
                    length = (int)(this.fileSplit[1] - offset);
                }

                try {
                    fileStream.seek(offset);
                    int dataOffset = (int)(offset - fileSplit[0]);
                    fileStream.read(data, dataOffset, length);

                    offset = offset + length + 1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
