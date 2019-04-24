package array.queue;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 课程题：打开转盘锁
 *
 * 我的思路就是DFS，每一轮进行穷举法
 *
 * 与最优算法的区别：
 * 1.因为4个数字是有限的组合，因此用了boolean[10000]数组来判断lock数字和已转数字，空间换时间
 * 2.直接转为数字，然后通过%位数得到4个位置的数字，比我互转方便且高效
 * 3.我用循环+2个方法来计算每一轮每个数字下8种可能，不如直接写死8种计算方法，更加效率，不过并不能省略一个循环处理
 *
 * 4.用head tail last来省略一个for循环，不知道是不是有效率提升？
 * 5.我用的LinkedBlockingDeque，应该用ArrayDeque就可以了，另外用了add加元素，用offer更配套一点
 *
 * 6.和同样用char的算法相比，他们使用StringBuilder.setCharAt方法，并且0和9的拨动直接一个三元判断就完事了，不用我那么麻烦
 **/
public class Class3_MyOpenLock {

    public int openLock(String[] deadends, String target) {
        String initNum = "0000";
        if (checkLock(deadends, initNum)) {
            return -1;
        }
        if (initNum.equals(target)) {
            return 0;
        }
        Queue<String> queue = new LinkedBlockingDeque<>();
        Set<String> rep = new HashSet<>();
        queue.add(initNum);
        int count = 0;
        // 每轮会有8种可能
        while(!queue.isEmpty()) {
            count++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String num = queue.poll();
                if (checkLock(deadends, num)) {
                    continue;
                }
                for (int j = 0; j < 4; j++) {
                    String forward = forward(num, j);
                    if (forward.equals(target)) {
                        return count;
                    }
                    if (!rep.contains(forward)) {
                        rep.add(forward);
                        queue.add(forward);
                    }
                    String back = back(num, j);
                    if (back.equals(target)) {
                        return count;
                    }
                    if (!rep.contains(back)) {
                        rep.add(back);
                        queue.add(back);
                    }
                }
            }
        }
        return -1;
    }

    private boolean checkLock(String[] deadends, String num) {
        for (int i = 0; i < deadends.length; i++) {
            if (num.equals(deadends[i])) {
                return true;
            }
        }
        return false;
    }

    private String forward(String num, int index) {
        char[] numArray = num.toCharArray();
        int next = (Integer.parseInt(String.valueOf(numArray[index])) + 1) % 10;
        numArray[index] = String.valueOf(next).toCharArray()[0];
        return new String(numArray);
    }

    private String back(String num, int index) {
        char[] numArray = num.toCharArray();
        int next = (Integer.parseInt(String.valueOf(numArray[index])) + 9) % 10;
        numArray[index] = String.valueOf(next).toCharArray()[0];
        return new String(numArray);
    }


    class OpenLockBest {

        boolean[] cube = new boolean[10000];
        int target;
        int level = 0;
        int head, tail, last;
        Queue<Integer> q = new ArrayDeque<>();

        public int openLock(String[] deadends, String target0) {
            for (String s: deadends)
                cube[Integer.parseInt(s)] = true;

            target = Integer.parseInt(target0);

            if (cube[0] == true || cube[target] == true)
                return -1;
            if (target == 0)
                return 0;
            cube[0] = true;
            last = 0;
            q.offer(last);
            tail = last;

            while(!q.isEmpty()) {
                int x, y, z, w;
                head = q.poll();

                x = (head/1000)%10;
                y = (head/100)%10;
                z = (head/10)%10;
                w = head%10;

                int[] orders = tentacle0(x, y, z, w);
                for (int i=0; i<8; i++){
                    int order = orders[i];
                    if (cube[order] == true)
                        continue;
                    if (order == target)
                        return level + 1;

                    last = order;
                    q.offer(last);
                    cube[order] = true;

                }
                if (head == tail) {
                    tail = last;
                    level++;
                }
            }
            return -1;

        }

        int[] tentacle0(int x, int y, int z, int w) {
            int[] r = new int[8];
            r[0] = ((x+1+10)%10)*1000 + ((y+10)%10)*100 + ((z+10)%10)*10 + (w+10)%10;
            r[1] = ((x-1+10)%10)*1000 + ((y+10)%10)*100 + ((z+10)%10)*10 + (w+10)%10;
            r[2] = ((x+10)%10)*1000 + ((y+1+10)%10)*100 + ((z+10)%10)*10 + (w+10)%10;
            r[3] = ((x+10)%10)*1000 + ((y-1+10)%10)*100 + ((z+10)%10)*10 + (w+10)%10;
            r[4] = ((x+10)%10)*1000 + ((y+10)%10)*100 + ((z+1+10)%10)*10 + (w+10)%10;
            r[5] = ((x+10)%10)*1000 + ((y+10)%10)*100 + ((z-1+10)%10)*10 + (w+10)%10;
            r[6] = ((x+10)%10)*1000 + ((y+10)%10)*100 + ((z+10)%10)*10 + (w+1+10)%10;
            r[7] = ((x+10)%10)*1000 + ((y+10)%10)*100 + ((z+10)%10)*10 + (w-1+10)%10;
            return r;
        }
    }


    class OtherOpenLock {

        public int openLock(String[] deadends, String target) {
            boolean[] isVisited=new boolean[10000];//0000-9999共10000种可能
            boolean[] isDead=new boolean[10000];
            for (String deadNum : deadends) {
                int dead = Integer.parseInt(deadNum);
                isDead[dead] = true;
            }
            if (isDead[0]) {//0000不能包含在死亡数组deadends中
                return -1;//0000在isDead中0位置，如果它是true，表明0000在deadends中，无效
            }
            if ("0000".equals(target)) {//0000不能是目标排列，否则，不用旋转操作，直接返回零
                return 0;
            }
            int step=0;
            Queue<Integer> queue=new ArrayDeque<>();
            queue.offer(Integer.parseInt(target));
            //将target保存在queue中，作为根节点，查找到“0000”，表示找到了target,退出
            //此处，ArrayDeque queue当作queue来用，而不是当作Stack来用，所以只能用offer和poll,不能用push和pop
            isVisited[Integer.parseInt(target)]=true;
            int last=Integer.parseInt(target);
            int cenglast=last;

            while(!queue.isEmpty()){
                while (true) {
                    int[] nei=new int[8];
                    int head=queue.poll();

                    if (head==0) {
                        return step;  //查找到“0000”退出
                    }
                    nei=neighbor(head);
                    for(int i=0;i<8;i++){
                        int trans=nei[i];
                        if(isDead[trans] || isVisited[trans])
                            continue;
                        queue.offer(trans);
                        last=trans;
                        isVisited[trans]=true;
                    }
                    if (head==cenglast) {
                        break;
                    }
                }
                step++;
                cenglast=last;
            }
            return -1;
        }

        private int[] neighbor(int code){
            int[] res=new int[8];
            int a=code%10;
            int b=code/10%10;
            int c=code/100%10;
            int d=code/1000%10;

            res[0]=d*1000+c*100+b*10+(a-1+10)%10;//向下取一位：9->8,2->1,1->0
            res[1]=d*1000+c*100+b*10+(a+1)%10;//向上取一位：9->0,2->3,0->1

            res[2]=d*1000+c*100+(b-1+10)%10*10+a;//向下取一位：9->8,2->1,1->0
            res[3]=d*1000+c*100+(b+1)%10*10+a;//向下取一位：9->8,2->1,1->0

            res[4]=d*1000+(c-1+10)%10*100+b*10+a;//向下取一位：9->8,2->1,1->0
            res[5]=d*1000+(c+1)%10*100+b*10+a;//向上取一位：9->0,2->3,0->1

            res[6]=(d-1+10)%10*1000+c*100+b*10+a;//向下取一位：9->8,2->1,1->0
            res[7]=(d+1)%10*1000+c*100+b*10+a;//向上取一位：9->0,2->3,0->1

            return res;
        }
    }


    class otherOpenLock2 {
        public int openLock(String[] deadends, String target) {
            Set<String> dead = new HashSet<>(Arrays.asList(deadends));//禁忌字符串集合
            Set<String> visited = new HashSet<>();//已访问字符串集合
            String init = "0000";
            if (dead.contains(init) || dead.contains(target)) {
                return -1;//初始密码或目标密码是禁忌值
            }

            if (target.equals(init)) {
                return 0;//初始密码是目标密码
            }

            Set<String> set1 = new HashSet<>();//存放从初始值衍生的节点
            set1.add(init);
            Set<String> set2 = new HashSet<>();//存放从目标衍生来的节点
            set2.add(target);

            int steps = 0;
            while (!set1.isEmpty() && !set2.isEmpty()) {
                if (set1.size() > set2.size()) {
                    Set<String> temp = set1;
                    set1 = set2;
                    set2 = temp;
                }//永远搜索短的那个结集合

                Set<String> set3 = new HashSet<>();
                for (String cur : set1) {
                    for (String next : getNexts(cur)) {
                        if (set2.contains(next)) {
                            return steps + 1;
                        }
                        if (!dead.contains(next) && !visited.contains(next)) {
                            visited.add(next);
                            set3.add(next);
                        }
                    }
                }

                steps++;
                set1 = set3;//新集合覆盖
            }

            return -1;
        }

        private List<String> getNexts(String cur) {
            List<String> nexts = new LinkedList<>();
            for (int i = 0; i < cur.length(); ++i) {
                char ch = cur.charAt(i);

                char newCh = ch == '0' ? '9' : (char) (ch - 1);
                StringBuilder builder = new StringBuilder(cur);
                builder.setCharAt(i, newCh);
                nexts.add(builder.toString());

                newCh = ch == '9' ? '0' : (char) (ch + 1);
                builder = new StringBuilder(cur);
                builder.setCharAt(i, newCh);
                nexts.add(builder.toString());
            }

            return nexts;
        }
    }

}
