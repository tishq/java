/**
 * @Author: 孟红全
 * @Date: 2019/8/14 上午10:45
 * @Version 1.0
 */
class Solution {
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] == target - nums[i]) {
                    return new int[] { i, j };
                }
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nu = {2, 7, 11, 15};
        int tag = 9;
        int so[];
        so =solution.twoSum(nu,tag);
        System.out.println(so.toString());

    }
}


