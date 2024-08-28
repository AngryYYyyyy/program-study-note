# 1.两数之和

给定一个整数数组 `nums` 和一个整数目标值 `target`，请你在该数组中找出 **和为目标值** *`target`* 的那 **两个** 整数，并返回它们的数组下标。

你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。

你可以按任意顺序返回答案。

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int n=nums.length;
        HashMap<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<n;i++){
            if(map.containsKey(target-nums[i])){
                return new int[]{i,map.get(target-nums[i])};
            }
            map.put(nums[i],i);
        }
        return new int[]{-1,-1};
    }
}
```

# 2. 两数相加

给你两个 **非空** 的链表，表示两个非负的整数。它们每位数字都是按照 **逆序** 的方式存储的，并且每个节点只能存储 **一位** 数字。

请你将两个数相加，并以相同形式返回一个表示和的链表。

你可以假设除了数字 0 之外，这两个数都不会以 0 开头。

时间复杂度$O(N)$、空间复杂度$O(N)$

```java
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0); 
        ListNode current = dummyHead;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int sum = carry;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next; 
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next; 
            }
            
            carry = sum / 10; 
            current.next = new ListNode(sum % 10); 
            current = current.next; 
        }
        
        return dummyHead.next; 
    }
}
```

时间复杂度$O(N)$、空间复杂度$O(1)$

**分析：**

为了避免额外的空间开销，我们可以选择长度较长的链表作为基础进行操作，直接在其上累加较短链表的对应节点的值。通过这种方法，我们主要在原链表上修改，从而减少了新节点的创建需求。这样，只有在较短链表已完全合并，而长链表还存在未处理节点时，才可能因为进位而需要额外处理长链表的剩余部分。

我们可以使用一个计数器 `count` 来确定哪个链表更长，这可以在一开始通过遍历两个链表来实现。此外，使用一个指针 `pre` 记录当前处理的最后一个节点非常重要，这样如果在链表末尾处理完成后还存在进位，我们可以便捷地在链表尾部添加新节点来处理这一进位，从而保证结果的正确性。

```java
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if(l1==null){return l2;}
        if(l2==null){return l1;}
        int count=0;
        ListNode tmp=l1;
        while(tmp!=null){
            tmp=tmp.next;
            count++;
        }
        tmp=l2;
        while(tmp!=null){
            tmp=tmp.next;
            count--;
        }
        ListNode newHead=count>0?l1:l2;
        ListNode cur=newHead;
        int carry=0;
        ListNode pre=null;
        while(l1!=null&&l2!=null){
            int num=carry+l1.val+l2.val;
            carry=0;
            if(num>=10){
                carry=1;
                num%=10;
            }
            cur.val=num;
            pre=cur;
            cur=cur.next;
            l1=l1.next;
            l2=l2.next;
        }
        
        while(carry==1&&cur!=null){
            int num=cur.val+carry;
             carry=0;
            if(num>=10){
                carry=1;
                num%=10;
            }
            cur.val=num;
            pre=cur;
            cur=cur.next;
        }
        if(carry==1){
            pre.next=new ListNode(1);
        }
        return newHead;
    }
}
```

# 3.无重复字符的最长子串

给定一个字符串 `s` ，请你找出其中不含有重复字符的 **最长子串**的长度。

时间复杂度$O(N)$、空间复杂度$O(N)$

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();  // 存储当前子串的字符集合
        char[] str = s.toCharArray();  // 将字符串转换为字符数组
        int left = 0;  // 滑动窗口左指针
        int ans = 0;  // 存储最长子串的长度
        for (int right = 0; right < str.length; right++) {  // 使用 for 循环控制右指针
            while (set.contains(str[right])) {  // 当发现重复字符时，移动左指针
                set.remove(str[left++]);  // 移除左指针字符并右移左指针
            }
            set.add(str[right]);  // 将新字符加入到集合中
            ans = Math.max(ans, right - left + 1);  // 更新最长子串的长度
        }
        return ans;  // 返回最长子串的长度
    }
}
```

# 4.寻找两个正序数组的中位数

给定两个大小分别为 `m` 和 `n` 的正序（从小到大）数组 `nums1` 和 `nums2`。请你找出并返回这两个正序数组的 **中位数** 。

算法的时间复杂度应该为 `O(log (m+n))` 。

**分析：**在解决这一问题之前，我们首先需要考虑如何从两个**等长**、**正序排列**的数组 `nums1` 和 `nums2` 中找到中位数。

对于偶数长度的数组，如果数组长度为 `len`，数组总长度则为 `2*len`，我们的目标是找到这两个数组合并后的第 `len` 小的元素。观察两个数组中点位置的元素 `mid1` 和 `mid2`，它们分别位于数组的 `len/2` 位置。如果 `mid1` 与 `mid2` 相等，那么任一元素都可以直接作为中位数。如果 `nums1[mid1] > nums2[mid2]`，则 `mid1` 及其之后的元素都不可能是中位数，因为这些元素至少是第 `len + 1` 小的位置。相应地，`nums2` 中 `mid2` 及其之前的元素也同样不可能是中位数，因为这些元素最多是第 `len - 1` 小的位置。反之，如果 `nums1[mid1] < nums2[mid2]`，处理逻辑类似，但是排除的区间相反。

通过排除上述不可能的元素，我们可以将搜索范围减半，然后再对剩余的部分重复上述过程。这是一种典型的二分递归方法。当数组缩减到只剩下一个可能的中位数时，由于我们是求偶数数组的上中位数，较小的那个值就是我们需要的中位数。

下面举例进行说明：

考虑两个等长的数组 `num1 = [1, 2, 3, 4]` 和 `num2 = [a, b, c, d]`，这里的数表示的是顺序，不是具体数值。假设数组长度为 4，总长度为 8。中位数（上中位数）是第 4 小的数。令 `mid1` 和 `mid2` 分别指向两个数组的中位数位置，即 `2` 和 `b`。如果 `2` 等于 `b`，则它们即为所求的中位数之一。

如果 `2 > b`，考虑到`3` 和 `4` 的最佳位置只能在第 5 和第 6 小，无法满足中位数的要求，因此可以排除。对于 `1` 和 `2`，在最佳情况下它们只能是第 2 和第 3 小，也不满足要求。因此，我们减小范围至 `[1, 2]` 和 `[c, d]` 进行进一步的中位数寻找，通过重复二分过程最终缩减到单个可能的值为止。

处理奇数长度数组时，逻辑与偶数数组相似，但在某些情况下会出现长度不等的情况。这时，需要额外判断中间值（`mid`），并考虑其是否可能是中位数。

下面举例进行说明：

对于数组 `num1 = [1, 2, 3, 4, 5]` 和 `num2 = [a, b, c, d, e]`，长度为 5，总长度为 10，中位数是第 5 小的数。设 `mid1` 和 `mid2` 分别指向 `3` 和 `c`。如果 `3` 等于 `c`，则它即为所求的中位数。

如果 `3 > c`，则可以排除 `num1` 中的 `3, 4, 5` 和 `num2` 中的 `a, b`。这会导致剩下的数组长度不一致。在这种情况下，我们需要独立判断 `c` 是否可能为中位数。`c` 必须大于或等于 `2` ，这样 `c` 才能位于序列 `(1, 2, a, b) c (3, 4, 5)` 的中间，符合中位数的条件。如果 `c` 小于 `2`，则将其排除，对 `[1, 2]` 和 `[d, e]` 进行进一步搜索。

```java
public static int getUpMedian(int[] A, int s1, int e1, int[] B, int s2, int e2) {
    int mid1=0;
    int mid2=0;
    while(s1<e1){
        mid1=(e1+s1)/2;
        mid2=(e2+s2)/2;
        if(A[mid1]==B[mid2]){
            return A[mid1];
        }
        if(((e1-s1+1)&1)==1){
            //奇数
            if(A[mid1]>B[mid2]){
                if(B[mid2]>=A[mid1-1]){
                    return B[mid2];
                }else{
                    e1=mid1-1;
                    s2=mid2+1;
                }
            }else{
                //A[mid1]<B[mid2]
                if(A[mid1]>=B[mid2-1]){
                    return A[mid1];
                }else{
                    e2=mid2-1;
                    s1=mid1+1;
                }
            }
        }else{
            //偶数
            if(A[mid1]>B[mid2]){
                e1=mid1;
                s2=mid2+1;
            }else{
                //A[mid1]<B[mid2]
                e2=mid2;
                s1=mid1+1;
            }
        }
    }
    return Math.min(A[s1],B[s2]);
}
```

解决了这一问题，我们可以进一步思考对于两个大小分别为 `m` 和 `n` 的正序数组 `nums1` 和 `nums2`，找到数组第k小的数。

假设 `nums1` 和 `nums2` 中较长的数组为 `longs`，长度为 `l`，较短的数组为 `shorts`，长度为 `s`。

 当 k <= s 时，在这种情况下，`k` 的值不大于较短数组 `shorts` 的长度。这意味着两个数组的前 `k` 个元素中就包含了第 `k` 小的元素。我们可以从两个数组中各取前 `k` 个元素，然后使用寻找中位数的方法来找到这 `2k` 个数中的第 `k` 小的数。

 当 k > l 时，在此情形中，`k` 的值大于较长数组 `longs` 的长度。这种情况下，我们可以直接排除一些不可能是第 `k` 小的元素的部分：

- 从 `longs` 中排除前 `k-s` 个元素，因为即使 `shorts` 中的所有元素都小于这部分元素，这些元素仍然无法成为第 `k` 小的数。
- 从 `shorts` 中排除前 `k-l` 个元素，理由同上。

处理完排除后，我们剩下 `(l+s-k+1)*2` 个元素。此时我们需要在剩下的数组中找到第 `l+s-k+1` 小的元素。这里我们需要注意，排除元素的个数为`2*k-l-s-2`，与剩余数组的`l+s-k+1 `  相加并为`k-1`，并没有得到第k小的数 ，因此我们需要比较 `longs[k-s]` 和 `shorts[s]` 的大小，以及 `shorts[k-l]` 和 `longs[l]` 的大小，如果均排除后，可以保证后续确定第 `k` 小的元素。

当 s < k <= l 时，这种情况比较复杂，因为 `k` 处于两个数组长度之间。在这种情况下，我们可以排除以下部分：

- 从 `longs` 中排除前 `k-s` 个元素和后 `l-k` 个元素。

在处理这些元素时，发现处理后数组并不等长，我们需要特别关注 `longs[k-s]` 是否大于等于 `shorts[s]`。如果是，那么 `longs[k-s]` 可能就是第 `k` 小的数。否则，我们需要在剩余的等长的数组中继续搜索。

```java
public static int findKthNum(int[] arr1, int[] arr2, int kth) {
		int[] longs = arr1.length >= arr2.length ? arr1 : arr2;
		int[] shorts = arr1.length < arr2.length ? arr1 : arr2;
		int l = longs.length;
		int s = shorts.length;
		if (kth <= s) {
			return getUpMedian(shorts, 0, kth - 1, longs, 0, kth - 1);
		}
		if (kth > l) {
			if (shorts[kth - l - 1] >= longs[l - 1]) {
				return shorts[kth - l - 1];
			}
			if (longs[kth - s - 1] >= shorts[s - 1]) {
				return longs[kth - s - 1];
			}
			return getUpMedian(shorts, kth - l, s - 1, longs, kth - s, l - 1);
		}
		if (longs[kth - s - 1] >= shorts[s - 1]) {
			return longs[kth - s - 1];
		}
		return getUpMedian(shorts, 0, s - 1, longs, kth - s, kth - 1);
	}
```

此时对于原题目，就很容易解答了，并且算法的时间复杂度为 `O(log min(m,n))` 

```java
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    int size = nums1.length + nums2.length;
    boolean even = (size & 1) == 0;
    if (nums1.length != 0 && nums2.length != 0) {
        if (even) {
            return (double) (findKthNum(nums1, nums2, size / 2) + findKthNum(nums1, nums2, size / 2 + 1)) / 2D;
        } else {
            return findKthNum(nums1, nums2, size / 2 + 1);
        }
    } else if (nums1.length != 0) {
        if (even) {
            return (double) (nums1[(size - 1) / 2] + nums1[size / 2]) / 2;
        } else {
            return nums1[size / 2];
        }
    } else if (nums2.length != 0) {
        if (even) {
            return (double) (nums2[(size - 1) / 2] + nums2[size / 2]) / 2;
        } else {
            return nums2[size / 2];
        }
    } else {
        return 0;
    }
}
```

# 7.整数反转

给你一个 32 位的有符号整数 `x` ，返回将 `x` 中的数字部分反转后的结果。

如果反转后整数超过 32 位的有符号整数的范围 `[−2^31, 2^31 − 1]` ，就返回 0。

```java
class Solution {
    public int reverse(int x) {
        boolean neg = ((x >>> 31) & 1) == 1;
		x = neg ? x : -x;
        int M=Integer.MIN_VALUE/10;
        int N=Integer.MIN_VALUE%10;
        int res=0;
        while(x!=0){
            if(res<M||(res==M&&x%10<N)){
                return 0;
            }
            res=res*10+x%10;
            x/=10;
        }
        return neg ? res : -res;
    }
}
```

# 8.字符串转换为整数

请你来实现一个 `myAtoi(string s)` 函数，使其能将字符串转换成一个 32 位有符号整数（类似 C/C++ 中的 `atoi` 函数）。

函数 `myAtoi(string s)` 的算法如下：

1. 读入字符串并丢弃无用的前导空格
2. 检查下一个字符（假设还未到字符末尾）为正还是负号，读取该字符（如果有）。 确定最终结果是负数还是正数。 如果两者都不存在，则假定结果为正。
3. 读入下一个字符，直到到达下一个非数字字符或到达输入的结尾。字符串的其余部分将被忽略。
4. 将前面步骤读入的这些数字转换为整数（即，"123" -> 123， "0032" -> 32）。如果没有读入数字，则整数为 `0` 。必要时更改符号（从步骤 2 开始）。
5. 如果整数数超过 32 位有符号整数范围 `[−231, 231 − 1]` ，需要截断这个整数，使其保持在这个范围内。具体来说，小于 `−231` 的整数应该被固定为 `−231` ，大于 `231 − 1` 的整数应该被固定为 `231 − 1` 。
6. 返回整数作为最终结果。

```java
class Solution {
    public static int myAtoi(String s) {
		if (s == null || s.equals("")) {
			return 0;
		}
		s = removeHeadZero(s.trim());
		if (s == null || s.equals("")) {
			return 0;
		}
		char[] str = s.toCharArray();
		if (!isValid(str)) {
			return 0;
		}
		boolean posi = str[0] == '-' ? false : true;
		int minq = Integer.MIN_VALUE / 10;
		int minr = Integer.MIN_VALUE % 10;
		int res = 0;
		int cur = 0;
		for (int i = (str[0] == '-' || str[0] == '+') ? 1 : 0; i < str.length; i++) {
			// 3  cur = -3   '5'  cur = -5    '0' cur = 0
			cur = '0' - str[i];
			if ((res < minq) || (res == minq && cur < minr)) {
				return posi ? Integer.MAX_VALUE : Integer.MIN_VALUE;
			}
			res = res * 10 + cur;
		}
		// res 负
		if (posi && res == Integer.MIN_VALUE) {
			return Integer.MAX_VALUE;
		}
		return posi ? -res : res;
	}

	public static String removeHeadZero(String str) {
		boolean r = (str.startsWith("+") || str.startsWith("-"));
		int s = r ? 1 : 0;
		for (; s < str.length(); s++) {
			if (str.charAt(s) != '0') {
				break;
			}
		}
		// s 到了第一个不是'0'字符的位置
		int e = -1;
		// 左<-右
		for (int i = str.length() - 1; i >= (r ? 1 : 0); i--) {
			if (str.charAt(i) < '0' || str.charAt(i) > '9') {
				e = i;
			}
		}
		// e 到了最左的 不是数字字符的位置
		return (r ? String.valueOf(str.charAt(0)) : "") + str.substring(s, e == -1 ? str.length() : e);
	}

	public static boolean isValid(char[] chas) {
		if (chas[0] != '-' && chas[0] != '+' && (chas[0] < '0' || chas[0] > '9')) {
			return false;
		}
		if ((chas[0] == '-' || chas[0] == '+') && chas.length == 1) {
			return false;
		}
		// 0 +... -... num
		for (int i = 1; i < chas.length; i++) {
			if (chas[i] < '0' || chas[i] > '9') {
				return false;
			}
		}
		return true;
	}
}
```



# 9.回文数

给你一个整数 `x` ，如果 `x` 是一个回文整数，返回 `true` ；否则，返回 `false` 。

回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。

- 例如，`121` 是回文，而 `123` 不是。

# 10. 正则表达式匹配

给你一个字符串 `s` 和一个字符规律 `p`，请你来实现一个支持 `'.'` 和 `'*'` 的正则表达式匹配。

- `'.'` 匹配任意单个字符
- `'*'` 匹配零个或多个前面的那一个元素

所谓匹配，是要涵盖 **整个** 字符串 `s`的，而不是部分字符串。

**分析：**

该模型为样本对应模型（行列模型），采用动态规划的方法，先解决暴力递归。

**Base Case**

1. **两者同时到达末尾**：如果 `s` 和 `p` 同时为空，表示完全匹配，返回 `true`。
2. **模式先到达末尾**：如果 `p` 为空但 `s` 不为空，直接返回 `false`。
3. **字符串先到达末尾**：如果 `s` 为空但 `p` 不为空，不立即返回 `false`，因为存在 `'*'` 可以表示零个字符的情况。例如，`p` 为 `"a*b*c*"` 可以匹配空字符串。

**递归逻辑**

当处理当前字符时，分以下情况讨论：

1. **下一个字符不是 `'\*'`**：如果当前字符匹配（`s[0] == p[0]` 或 `p[0] == '.'`），则继续递归匹配剩下的字符串 `proecess(i+1,j+1)`。

2. 下一个字符是 `'*'`

   ：

   - **匹配零次**：直接忽略模式中的 `x*`，递归检查 `proecess(i,j+2)`。
   - **匹配多次**：如果当前字符 `s[0]` 与模式 `p[0]` 匹配（或 `p[0] == '.'`），则 `s` 可以去掉首字符继续递归匹配 `proecess(i+1,j)`。

```java
class Solution {
    public boolean isMatch(String s, String p) {
        return process(s.toCharArray(),p.toCharArray(),0,0);
    }
    public boolean process(char[]s,char[] p,int i,int j){
        //basecase
        if(i==s.length){
            if(j==p.length){
                return true;
            }
            return j+1!=p.length&&p[j+1]=='*'&&process(s,p,i,j+2);
        }
        if(j==p.length){
            return false;
        }
        //
        if(j+1==p.length||p[j+1]!='*'){
            return (p[j]=='.'||p[j]==s[i])&&process(s,p,i+1,j+1);
        }else{
            boolean p1=process(s,p,i,j+2);
            boolean p2= (p[j]=='.'||p[j]==s[i])&&process(s,p,i+1,j);
            return p1||p2;
        }
    }
}
```

优化为时间复杂度为m*n的动态规划

定义了一个二维布尔数组 `dp`，其中 `dp[i][j]` 表示字符串 `s` 从位置 `i` 到末尾与模式 `p` 从位置 `j` 到末尾是否匹配。数组的大小为 `(n+1) x (m+1)`，其中 `n` 是字符串 `s` 的长度，`m` 是模式 `p` 的长度。

**初始化步骤：**

1. **最终状态**：`dp[n][m] = true`，表示两个空序列是匹配的（即当字符串和模式都已完全遍历时）。
2. **模式后缀处理**：从模式的倒数第二个位置开始，如果字符后面紧跟一个 `'*'`，表示该字符可以出现0次或多次。如果下一个状态 `dp[n][j+2]` 为 `true`（即忽略这两个字符后模式为空），则当前状态也应为 `true`。

**动态规划逻辑**

使用两层循环从后向前遍历字符串 `s` 和模式 `p` 来填充 `dp` 表：

- **外层循环**：遍历字符串的每个位置 `i`，从最后一个字符向前到第一个字符。
- **内层循环**：对于每个字符串位置 `i`，遍历模式的每个位置 `j`，从最后一个字符向前到第一个字符。

**状态转移：**

- **不含星号**：如果模式的下一个字符 `j+1` 不是 `'*'`，则当前字符必须匹配（即 `s[i]` 应等于 `p[j]` 或 `p[j]` 是 `'.'`）。此外，接下来的字符串和模式的剩余部分也必须匹配，即 `dp[i][j]` 取决于 `dp[i+1][j+1]`。
- **含星号**：如果模式的下一个字符 `j+1` 是 `'*'`，存在两种情况：
  - **忽略模式中的当前字符和 `'\*'`**：这相当于 `p[j]` 和 `'*'` 被视为不出现，因此 `dp[i][j]` 取决于 `dp[i][j+2]`。
  - **使用模式中的当前字符**：如果字符串中的当前字符与模式中的当前字符匹配（`s[i]` 等于 `p[j]` 或 `p[j]` 是 `'.'`），则可以考虑忽略字符串中的当前字符并再次使用模式，即 `dp[i][j]` 还取决于 `dp[i+1][j]`。

- **最终返回值**：`dp[0][0]`，表示整个字符串 `s` 与整个模式 `p` 是否匹配。

```java
class Solution {
    public boolean isMatch(String str, String pattern) {
        char[]s=str.toCharArray();
        char[]p=pattern.toCharArray();
        int n = s.length;
		int m = p.length;
        boolean[][]dp=new boolean[n+1][m+1];
        dp[n][m]=true;
        for(int j=m-2;j>=0;j--){
            dp[n][j]=p[j+1]=='*'&&dp[n][j+2];
        }
        for(int i=n-1;i>=0;i--){
            for(int j=m-1;j>=0;j--){
                if(j+1==p.length||p[j+1]!='*'){
                    dp[i][j]= (p[j]=='.'||p[j]==s[i])&&dp[i+1][j+1];
                }else{
                    boolean p1=dp[i][j+2];
                    boolean p2= (p[j]=='.'||p[j]==s[i])&&dp[i+1][j];
                    dp[i][j]= p1||p2;
                }
            }
        }
        return dp[0][0];
    }
}
```

# [11. 盛最多水的容器](https://leetcode.cn/problems/container-with-most-water/)

给定一个长度为 `n` 的整数数组 `height` 。有 `n` 条垂线，第 `i` 条线的两个端点是 `(i, 0)` 和 `(i, height[i])` 。找出其中的两条线，使得它们与 `x` 轴共同构成的容器可以容纳最多的水。返回容器可以储存的最大水量。

**说明：**你不能倾斜容器。

**分析：**核心思想为每一次尝试都是朝着可能更优的方向进行。通过合理地移动指针，我们可以排除那些不可能是最优解的情况，从而保证每次调整指针时都有可能找到更大的容量。我们从可能的最大宽度（即两端的线段）开始，保证初始状态是宽度最大的情况。通过比较两端线段的高度，我们决定舍弃较短的一端。这是基于这样的逻辑：较短的线段是限制因素，而移动较长的线段不可能增加容量（因为宽度在减小，而高度最多不变）。每次移动指针，我们都在试图找到一个更高的线段来替换当前较短的线段，这可能会带来更大的容量。	时间复杂度为 O(n)

**基本思路**

1. **初始化两个指针**：一个在数组的开始（`l`），另一个在数组的末尾（`r`）。
2. **计算容积**：在每一步，计算由这两个指针表示的线和x轴形成的容器的容积，并更新最大容积。
3. **移动指针**：为了尝试找到一个能够容纳更多水的容器，移动较矮的线的指针（向中间靠拢）。这是因为移动较矮的线可能会找到一个更高的线从而增加容器的高度，而移动较高的线只会减少容器的宽度，不可能增加容积。

```java
class Solution {
    public int maxArea(int[] height) {
        int l=0;
        int r=height.length-1;
        int ans=0;
        while(l!=r){
            if(height[l]>height[r]){
                ans=Math.max(ans,(r-l)*height[r]);
                r--; 
            }else{
                ans=Math.max(ans,(r-l)*height[l]);
                l++;
            }
        }
        return ans;
    }
}
```



# [12. 整数转罗马数字](https://leetcode.cn/problems/integer-to-roman/)

罗马数字包含以下七种字符： `I`， `V`， `X`， `L`，`C`，`D` 和 `M`。

```
字符          数值
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
```

例如， 罗马数字 2 写做 `II` ，即为两个并列的 1。12 写做 `XII` ，即为 `X` + `II` 。 27 写做 `XXVII`, 即为 `XX` + `V` + `II` 。

通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 `IIII`，而是 `IV`。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 `IX`。这个特殊的规则只适用于以下六种情况：

- `I` 可以放在 `V` (5) 和 `X` (10) 的左边，来表示 4 和 9。
- `X` 可以放在 `L` (50) 和 `C` (100) 的左边，来表示 40 和 90。 
- `C` 可以放在 `D` (500) 和 `M` (1000) 的左边，来表示 400 和 900。

给你一个整数，将其转为罗马数字。

```java
class Solution {
    public String intToRoman(int num) {
        String[][] c = { 
				{ "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" },
				{ "", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC" },
				{ "", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM" },
				{ "", "M", "MM", "MMM" } };
        StringBuilder ans=new StringBuilder();
        ans.append(c[3][num/1000])
        .append(c[2][num/100%10])
        .append(c[1][num/10%10])
        .append(c[0][num%10]);
        return ans.toString();
    }
}
```

# 13. 罗马数字转整数

给定一个罗马数字，将其转换成整数。

```java
class Solution {
    public static int romanToInt(String s) {
		// C     M     X   C     I   X
		// 100  1000  10   100   1   10
		int nums[] = new int[s.length()];
		for (int i = 0; i < s.length(); i++) {
			switch (s.charAt(i)) {
			case 'M':
				nums[i] = 1000;
				break;
			case 'D':
				nums[i] = 500;
				break;
			case 'C':
				nums[i] = 100;
				break;
			case 'L':
				nums[i] = 50;
				break;
			case 'X':
				nums[i] = 10;
				break;
			case 'V':
				nums[i] = 5;
				break;
			case 'I':
				nums[i] = 1;
				break;
			}
		}
		int sum = 0;
		for (int i = 0; i < nums.length - 1; i++) {
			if (nums[i] < nums[i + 1]) {
				sum -= nums[i];
			} else {
				sum += nums[i];
			}
		}
		return sum + nums[nums.length - 1];
	}
}
```



# 14. 最长公共前缀

编写一个函数来查找字符串数组中的最长公共前缀。

如果不存在公共前缀，返回空字符串 `""`。

```java
class Solution {
    public String longestCommonPrefix(String[] strs) {
        String prefix = strs[0];
        int min=prefix.length();
        for(String s : strs){
            int index=0;
            while(index<s.length()&&index<prefix.length()){
                if(s.charAt(index)!=prefix.charAt(index)){
                    break;
                }
                index++;
            }
            min=Math.min(min,index);
            if(min==0){
                return "";
            }
        }
        return prefix.substring(0,min);
    }
}
```

# [15. 三数之和](https://leetcode.cn/problems/3sum/)

给你一个整数数组 `nums` ，判断是否存在三元组 `[nums[i], nums[j], nums[k]]` 满足 `i != j`、`i != k` 且 `j != k` ，同时还满足 `nums[i] + nums[j] + nums[k] == 0` 。请

你返回所有和为 `0` 且不重复的三元组。

**注意：**答案中不可以包含重复的三元组。

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        List<List<Integer>> ans = new ArrayList();
        for (int i = n - 1; i >= 0; i--) {
            if (i == n - 1 || nums[i] != nums[i + 1]) {
                List<List<Integer>> nexts = twoSum(nums, i - 1, -nums[i]);
                for (List<Integer> cur : nexts) {
                    cur.add(nums[i]);
                    ans.add(cur);
                }
            }
        }
        return ans;
    }

    public List<List<Integer>> twoSum(int[] nums, int end, int targert) {
        List<List<Integer>> ans = new ArrayList();
        int l = 0;
        int r = end;
        while (l < r) {
            int sum = nums[l] + nums[r];
            if (sum > targert) {
                r--;
            } else if (sum < targert) {
                l++;
            } else {
                if (l == 0 || nums[l] != nums[l - 1]) {
                    List<Integer> cur = new ArrayList();
                    cur.add(nums[l]);
                    cur.add(nums[r]);
                    ans.add(cur);
                }
                l++;
            }
        }
        return ans;
    }
}
```

# 20.有效括号

> [20. 有效的括号 - 力扣（LeetCode）](https://leetcode.cn/problems/valid-parentheses/description/)

```java
class Solution {
   public boolean isValid(String s) {
        /*先入后出的特性*/
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '{' || c == '[') {
                /*简化后续判断*/
                stack.push(c=='('?')':(c=='{'?'}':']'));
            } else {
                if (stack.isEmpty()) return false;
                char top = stack.pop();
                if (top!=c) return false;
            }
        }
        return stack.isEmpty();
    }
}
```



# 26.删除有序数组的重复项

> [26. 删除有序数组中的重复项 - 力扣（LeetCode）](https://leetcode.cn/problems/remove-duplicates-from-sorted-array/)

```java
class Solution {
   public int removeDuplicates(int[] nums) {
        int ni=0;
        for(int i=0;i<nums.length;i++){
            if(nums[ni]!=nums[i]){
                nums[++ni]=nums[i];
            }
        }
        return ni+1;
    }
}
```

# 27.移除元素

> [27. 移除元素 - 力扣（LeetCode）](https://leetcode.cn/problems/remove-element/)

```java
class Solution {
    public int removeElement(int[] nums, int val) {
        int ni=0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]!=val){
                nums[ni++]=nums[i];
            }
        }
        return ni;
    }
}
```



# 28.找出字符串中第一个匹配项的下标

> [28. 找出字符串中第一个匹配项的下标 - 力扣（LeetCode）](https://leetcode.cn/problems/find-the-index-of-the-first-occurrence-in-a-string/description/)

```java
class Solution {
    public int strStr(String haystack, String needle) {
        for(int l=0;l<haystack.length();l++){
            int r=l;
            int i=0;
            while(haystack.charAt(r)==needle.charAt(i)){
                r++;
                i++;
                if(i==needle.length()){
                    return l;
                }
            }
            l++;
        }
        return -1;
    }

}
```

kmp

```java
class Solution {
    public int strStr(String haystack, String needle) {
        int[]next=getArrayNext(needle);
        int i=0,j=0;
        while(i<haystack.length()&&j<needle.length()){
            if(haystack.charAt(i)==needle.charAt(j)){
                /*匹配*/
                i++;
                j++;
            } else if (next[j]==-1) {
                /*j==0,还不匹配*/
                i++;
            }else{
                /*跳转到匹配的最大前缀后缀数组的下一个*/
                /*next[]，既代表长度，也代表前缀结束的下一位*/
                j=next[j];
            }
        }
        return j==needle.length()?i-j:-1;
    }

    private int[] getArrayNext(String needle) {
        if (needle.length() == 1) {
            return new int[] { -1 };
        }
        int[] next=new int[needle.length()];
        /*固定值*/
        next[0]=-1;
        next[1]=0;
        int i=2;
        /*与i-1比对的下标*/
        int ni=0;
        while(i<needle.length()){
            if(needle.charAt(ni)==needle.charAt(i-1)){
                /*匹配*/
                /*++ni，简化后续匹配*/
                next[i++]=++ni;
            } else if (ni>0) {
                ni=next[ni];
            }else{
                /*前面没有匹配的*/
                next[i++]=0;
            }
        }
        return next;
    }
}
```



# 53.最大子数组和

> [53. 最大子数组和 - 力扣（LeetCode）](https://leetcode.cn/problems/maximum-subarray/description/)

```java
class Solution {
    public int maxSubArray(int[] nums) {
        int currentMax = nums[0];
        int globalMax = nums[0];
        for (int i = 1; i < nums.length; i++) {
            currentMax = Math.max(nums[i], currentMax + nums[i]);
            globalMax = Math.max(globalMax, currentMax);
        }
        return globalMax;
    }
}
```

