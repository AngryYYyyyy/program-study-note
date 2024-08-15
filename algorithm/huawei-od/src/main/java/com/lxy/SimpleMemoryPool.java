package com.lxy;

import java.util.HashMap;
import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/7 22:25
 * @Description：
 */
public class SimpleMemoryPool {
    public static class MemoryPool {
        private final int[] memory; // 模拟内存的数组
        private final HashMap<Integer, Integer> allocations; // 存储内存分配，key为起始地址，value为块大小
        private static final int MEMORY_SIZE = 100; // 内存池大小为100字节

        public MemoryPool() {
            this.memory = new int[MEMORY_SIZE];
            this.allocations = new HashMap<>();
        }

        public String request(int size) {
            if (size <= 0 || size > MEMORY_SIZE) {
                return "error";
            }

            int start = findSpace(size);
            if (start == -1) {
                return "error";
            }

            // 标记内存为已分配
            for (int i = start; i < start + size; i++) {
                memory[i] = 1;
            }
            allocations.put(start, size);
            return Integer.toString(start);
        }

        public String release(int startAddress) {
            Integer size = allocations.get(startAddress);
            if (size == null) {
                return "error";
            }

            // 清除内存分配
            for (int i = startAddress; i < startAddress + size; i++) {
                memory[i] = 0;
            }
            allocations.remove(startAddress);
            return "";
        }

        private int findSpace(int size) {
            int currentFree = 0;

            for (int i = 0; i < MEMORY_SIZE; i++) {
                if (memory[i] == 0) {
                    if (++currentFree == size) {
                        return i - size + 1;
                    }
                } else {
                    currentFree = 0;
                }
            }

            return -1;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        scanner.nextLine();

        MemoryPool pool = new MemoryPool();
        for (int i = 0; i < N; i++) {
            String command = scanner.nextLine();
            String[] parts = command.split("=");
            if (parts[0].equals("REQUEST")) {
                int size = Integer.parseInt(parts[1]);
                System.out.println(pool.request(size));
            } else if (parts[0].equals("RELEASE")) {
                int address = Integer.parseInt(parts[1]);
                String result = pool.release(address);
                if (!result.isEmpty()) {
                    System.out.println(result);
                }
            }
        }
        scanner.close();
    }
}
