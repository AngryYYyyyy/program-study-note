package class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Question02 {
    public static class EnhancedHeap<T> {
        private ArrayList<T> heapElements;
        //反向索引表
        private HashMap<T, Integer> elementIndexMap;
        private int heapSize;
        private Comparator<? super T> comparator;

        public EnhancedHeap(Comparator<? super T> comparator) {
            this.heapElements = new ArrayList<>();
            this.elementIndexMap = new HashMap<>();
            this.heapSize = 0;
            this.comparator = comparator;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public int getSize() {
            return heapSize;
        }

        public boolean containsElement(T element) {
            return elementIndexMap.containsKey(element);
        }

        public T peekTop() {
            return heapElements.get(0);
        }

        public void add(T element) {
            heapElements.add(element);
            elementIndexMap.put(element, heapSize);
            siftUp(heapSize++);
        }

        public T removeTop() {
            T topElement = heapElements.get(0);
            swap(0, heapSize - 1);
            elementIndexMap.remove(topElement);
            heapElements.remove(--heapSize);
            siftDown(0);
            return topElement;
        }

        public void removeElement(T element) {
            if(!elementIndexMap.containsKey(element)) {
                return;
            }
            // 获取堆中最后一个元素
            T lastElement = heapElements.get(heapSize - 1);
            // 获取待删除元素在堆中的索引
            int index = elementIndexMap.get(element);
            // 从索引映射中移除该元素
            elementIndexMap.remove(element);
            // 减少堆的大小并移除最后一个元素
            heapElements.remove(--heapSize);
            // 如果待删除的元素不是堆中的最后一个元素
            if (!element.equals(lastElement)) {
                // 将最后一个元素移至待删除元素的位置
                heapElements.set(index, lastElement);
                // 更新最后一个元素的新位置
                elementIndexMap.put(lastElement, index);
                // 调整堆以保持堆性质
                adjustElement(lastElement);
            }
        }


        public void adjustElement(T element) {
            if(!elementIndexMap.containsKey(element)) {
                return;
            }
            siftUp(elementIndexMap.get(element));
            siftDown(elementIndexMap.get(element));
        }

        public List<T> getAllElements() {
            return new ArrayList<>(heapElements);
        }
        //向上调整
        private void siftUp(int index) {
            while (index > 0 && comparator.compare(heapElements.get(index), heapElements.get((index - 1) / 2)) < 0) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }
        //向下调整
        private void siftDown(int index) {
            int leftChildIndex = index * 2 + 1;
            while (leftChildIndex < heapSize) {
                int smallestChildIdx = leftChildIndex + 1 < heapSize &&
                        comparator.compare(heapElements.get(leftChildIndex + 1), heapElements.get(leftChildIndex)) < 0
                        ? leftChildIndex + 1
                        : leftChildIndex;
                smallestChildIdx = comparator.compare(heapElements.get(smallestChildIdx), heapElements.get(index)) < 0
                        ? smallestChildIdx
                        : index;
                if (smallestChildIdx == index) {
                    break;
                }
                swap(smallestChildIdx, index);
                index = smallestChildIdx;
                leftChildIndex = index * 2 + 1;
            }
        }

        private void swap(int i, int j) {
            T tempElement1 = heapElements.get(i);
            T tempElement2 = heapElements.get(j);
            heapElements.set(i, tempElement2);
            heapElements.set(j, tempElement1);
            elementIndexMap.put(tempElement2, i);
            elementIndexMap.put(tempElement1, j);
        }
    }

}
