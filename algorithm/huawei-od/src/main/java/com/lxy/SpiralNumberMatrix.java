package com.lxy;

import java.util.Scanner;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/12 17:40
 * @Description：
 */
public class SpiralNumberMatrix {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int row = sc.nextInt();
        int column=(n+row-1)/row;
        int[][] matrix=new int[row][column];
        transform(matrix,n);
        String[][] newMatrix = new String[row][column];
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                int cur=matrix[i][j];
                if(cur==0||cur>n){
                    newMatrix[i][j]="*";
                }else{
                    newMatrix[i][j]=String.valueOf(cur);
                }
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column ;j++) {
                System.out.print(newMatrix[i][j]+" ");
            }
            System.out.println();
        }
    }

    private static void transform(int[][]matrix,int n) {
        int number=1;

        int lap=0;
        while(number<=n){
            number=spiralFilling(matrix,lap,number,n);
            lap++;
        }
    }

    private static int spiralFilling(int[][] matrix, int lap,int number,int n) {
        int row=matrix.length;
        int column=matrix[0].length;
        for(int j=lap;j<column-lap;j++){
            matrix[lap][j]=number++;
            if(number>n){
                return number;
            }
        }
        for(int i=lap+1;i<row-lap;i++){
            matrix[i][column-lap-1]=number++;
            if(number>n){
                return number;
            }
        }
        for(int j=column-lap-1-1;j>=lap;j--){
            matrix[row-lap-1][j]=number++;
            if(number>n){
                return number;
            }
        }
        for(int i=row-lap-1-1;i>lap;i--){
            matrix[i][lap]=number++;
            if(number>n){
                return number;
            }
        }
        return number;
    }
}
