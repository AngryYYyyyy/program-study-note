package class07;

import java.util.*;
import class07.Question02.EnhancedHeap;

public class Question03 {
    public static class Customer {
        public int id;
        public int buy;
        public int enterTime;

        public Customer(int id, int buy, int enterTime) {
            this.id = id;
            this.buy = buy;
            this.enterTime = enterTime;
        }
    }

    public static class DescendingCustomerComparator implements Comparator<Customer> {
        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy != o2.buy ? (o2.buy - o1.buy) : (o1.enterTime - o2.enterTime);
        }
    }

    public static class AscendingCustomerComparator implements Comparator<Customer> {
        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy != o2.buy ? (o1.buy - o2.buy) : (o1.enterTime - o2.enterTime);
        }
    }

    public static class CustomerRankTracker {
        private HashMap<Integer, Customer> customers;
        private EnhancedHeap<Customer> candidateHeap;
        private EnhancedHeap<Customer> topHeap;
        private final int limit;

        public CustomerRankTracker(int limit) {
            customers = new HashMap<>();
            candidateHeap = new EnhancedHeap<>(new DescendingCustomerComparator());
            topHeap = new EnhancedHeap<>(new AscendingCustomerComparator());
            this.limit = limit;
        }

        public void processEvent(int time, int id, boolean isPurchase) {
            if (!isPurchase && !customers.containsKey(id)) {
                return;
            }
            customers.putIfAbsent(id, new Customer(id, 0, time));

            Customer customer = customers.get(id);
            customer.buy += isPurchase ? 1 : -1;
            customer.enterTime = time; // Update time if necessary

            if (customer.buy == 0) {
                customers.remove(id);
                candidateHeap.removeElement(customer);
                topHeap.removeElement(customer);
                return;
            }

            updateHeaps(customer);
        }

        private void updateHeaps(Customer customer) {
            if (!candidateHeap.containsElement(customer) && !topHeap.containsElement(customer)) {
                if (topHeap.getSize() < limit) {
                    topHeap.add(customer);
                } else {
                    candidateHeap.add(customer);
                }
            } else {
                candidateHeap.adjustElement(customer);
                topHeap.adjustElement(customer);
                moveIfNeeded();
            }
        }

        private void moveIfNeeded() {
            if (candidateHeap.isEmpty()) return;
            if (topHeap.getSize() < limit) {
                Customer nextTop = candidateHeap.removeTop();
                topHeap.add(nextTop);
            } else if (candidateHeap.peekTop().buy > topHeap.peekTop().buy) {
                Customer oldTop = topHeap.removeTop();
                Customer newTop = candidateHeap.removeTop();
                topHeap.add(newTop);
                candidateHeap.add(oldTop);
            }
        }

        public List<Integer> getCurrentTopCustomers() {
            List<Integer> result = new ArrayList<>();
            for (Customer customer : topHeap.getAllElements()) {
                result.add(customer.id);
            }
            return result;
        }
    }

    public static List<List<Integer>> trackTopKCustomers(int[] ids, boolean[] operations, int k) {
        List<List<Integer>> results = new ArrayList<>();
        CustomerRankTracker tracker = new CustomerRankTracker(k);
        for (int i = 0; i < ids.length; i++) {
            tracker.processEvent(i, ids[i], operations[i]);
            results.add(tracker.getCurrentTopCustomers());
        }
        return results;
    }
}
