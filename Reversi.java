package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Reversi extends JFrame{
    //游戏界面设计
    private boolean isBlackPlay=true;
    boolean gameover=false;
    private int blackChess=0,whiteChess=0;
    private int blackTotal =2,whiteTotal=2;
    private int[][] data=new int[8][8];

    private JPanel p1=new JPanel();
    private JButton button[][]=new JButton[8][8];

    //功能按钮
    private JPanel p2=new JPanel();
    private JButton renewB=new JButton("重新开始");
    private JButton exitB=new JButton("退出");

    //提示玩家,实时显示黑白棋数目
    private JPanel p3=new JPanel();
    String  player="黑棋："+blackTotal+"    "+"白棋："+whiteTotal+"  "+"请黑色方下棋";
    private JLabel l1=new JLabel(player);


    //构造函数
    Reversi(){
//添加控件
        Container c=this.getContentPane();
        c.add(p1, BorderLayout.CENTER);
        p1.setLayout(new GridLayout(8, 8));

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                button[i][j]=new JButton("");
            }
        }
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                p1.add(button[i][j]);
                button[i][j].setBackground(Color.gray);
                button[i][j].addActionListener(new Handler(i, j));
            }
        }

        button[3][3].setBackground(Color.WHITE);
        button[3][4].setBackground(Color.BLACK);
        button[4][3].setBackground(Color.BLACK);
        button[4][4].setBackground(Color.WHITE);



        c.add(p2, BorderLayout.SOUTH);
        p2.add(renewB);
        p2.add(exitB);

        renewB.addActionListener(new renewHandler());
        exitB.addActionListener(new exitHandler());
        c.add(p3,BorderLayout.NORTH);
        p3.add(l1);


    }

    class Handler implements ActionListener{
        int row=-1,col=-1;

        public Handler (int x,int y){//获取位置
            row = x;
            col = y;
        }
        //判断位置是否合法
        private boolean isValidPosition(int x, int y){
            if (button[x][y].getBackground()!=Color.gray)
                return false;
            if(isBlackPlay==true){
                //水平方向判断
                for(int j=0;j<8;j++){
                    if(button[x][j].getBackground()==Color.BLACK){
                        if((j-y)>=2){
                            int count=0;
                            for(int k=y;k<j;k++){
                                if(button[x][k].getBackground()==Color.WHITE)
                                    count++;
                            }
                            if(count==(j-y-1))//判断两个黑棋中间件是否全为白棋
                                return true;
                        }
                        if((y-j)>=2){
                            int count=0;
                            for(int k=y;k>j;k--){
                                if(button[x][k].getBackground()==Color.white)
                                    count++;
                            }
                            if(count==(y-j-1))
                                return true;
                        }
                    }
                }
                //垂直方向判断
                for(int i=0;i<8;i++){
                    if(button[i][y].getBackground()==Color.BLACK){
                        if((i-x)>=2){
                            int count=0;
                            for(int k=x;k<i;k++){
                                if(button[k][y].getBackground()==Color.white){
                                    count++;
                                }
                            }
                            if(count==(i-x-1)){
                                return true;
                            }
                        }
                        if((x-i)>=2){
                            int count=0;
                            for(int k=x;k>i;k--){
                                if(button[k][y].getBackground()==Color.WHITE){
                                    count++;
                                }
                            }
                            if(count==(x-i-1))
                                return true;
                        }
                    }
                }
                //斜线方向判断
                for(int i=0;i<8;i++){
                    for(int j=0;j<8;j++){
                        if(button[i][j].getBackground()==Color.BLACK){
                            if((x-i)==(y-j)&&(x-i)>=2){//右下
                                int yy=y;
                                int count=0;
                                for(int k=x;k>i;k--){
                                    if(button[k][yy].getBackground()==Color.WHITE)
                                        count++;
                                    yy--;
                                }
                                if(count==(x-i-1))
                                    return true;
                            }
                            if((x-i)==(j-y)&&(x-i)>=2){
                                int yy=y;
                                int count=0;
                                for(int k=x;k>i;k--){
                                    if(button[k][yy].getBackground()==Color.WHITE){
                                        count++;
                                    }
                                    yy++;
                                }
                                if(count==(x-i-1))
                                    return true;
                            }
                            if((i-x)==(y-j)&&(i-x)>=2){
                                int yy=y;
                                int count=0;
                                for(int k=x;k<i;k++){
                                    if(button[k][yy].getBackground()==Color.WHITE)
                                        count++;
                                    yy--;
                                }
                                if(count==(i-x-1))
                                    return true;
                            }
                            if((i-x)==(j-y)&&(i-x)>=2){
                                int yy=y;
                                int count=0;
                                for(int k=x;k<i;k++){
                                    if(button[k][yy].getBackground()==Color.WHITE)
                                        count++;
                                    yy++;
                                }
                                if(count==(i-x-1))
                                    return true;
                            }
                        }
                    }
                }
                return false;
            }
            else{//同理，若白棋下
                for (int i = 0; i < 8; i++)
                {
                    if (button[i][y].getBackground() == Color.WHITE)
                    {
                        if ((i - x) >= 2)
                        {
                            int count = 0;
                            for (int k = x; k < i; k++)
                            {
                                if (button[k][y].getBackground() == Color.BLACK)
                                    count++;
                            }
                            if (count == (i - x - 1))
                                return true;
                        }
                        if ((x - i) >= 2)
                        {
                            int count = 0;
                            for (int k = x; k > i; k--)
                            {
                                if (button[k][y].getBackground() == Color.BLACK)
                                    count++;
                            }
                            if (count == (x - i - 1))
                                return true;
                        }
                    }
                }

                for (int j = 0; j < 8; j++)
                {
                    if (button[x][j].getBackground() == Color.WHITE)
                    {
                        if ((j - y) >= 2)
                        {
                            int count = 0;
                            for (int k = y; k < j; k++)
                            {
                                if (button[x][k].getBackground() == Color.BLACK)
                                    count++;
                            }
                            if (count == (j - y - 1))
                                return true;
                        }

                        if ((y - j) >= 2)
                        {
                            int count = 0;
                            for (int k = y; k > j; k--)
                            {
                                if (button[x][k].getBackground() == Color.BLACK)
                                    count++;
                            }
                            if (count == (y - j - 1))
                                return true;
                        }

                    }
                }

                for (int i = 0; i < 8; i++)
                {
                    for (int j = 0; j < 8; j++)
                    {
                        if (button[i][j].getBackground() == Color.WHITE)
                        {
                            if ((x - i) == (y - j) && (x - i) >= 2)
                            {
                                int yy = y;
                                int count = 0;
                                for (int k = x; k > i; k--)
                                {
                                    if (button[k][yy].getBackground() == Color.BLACK)
                                    {
                                        count++;
                                    }
                                    yy--;

                                }
                                if (count == (x - i - 1))
                                    return true;
                            }

                            if ((x - i) == (j - y) && (x - i) >= 2)
                            {
                                int yy = y;
                                int count = 0;
                                for (int k = x; k > i; k--)
                                {
                                    if (button[k][yy].getBackground() == Color.BLACK)
                                    {
                                        count++;
                                    }
                                    yy++;

                                }
                                if (count == (x - i - 1))
                                    return true;
                            }

                            if ((i - x) == (y - j) && (i - x) >= 2)
                            {
                                int yy = y;
                                int count = 0;
                                for (int k = x; k < i; k++)
                                {
                                    if (button[k][yy].getBackground() == Color.BLACK)
                                    {
                                        count++;
                                    }
                                    yy--;
                                }
                                if (count == (i - x - 1))
                                    return true;
                            }

                            if ((i - x) == (j - y) && (i - x) >= 2)
                            {
                                int yy = y;
                                int count = 0;
                                for (int k = x; k < i; k++)
                                {
                                    if (button[k][yy].getBackground() == Color.BLACK)
                                    {
                                        count++;
                                    }
                                    yy++;
                                }
                                if (count == (i - x - 1))
                                    return true;
                            }

                        }
                    }
                }

                return false;
            }
            }
            //翻转棋子
        private void refresh(int x, int y)
        {
            if (isBlackPlay == true)// 若是黑方下
            {
                for (int i = 0; i < 8; i++)
                {
                    if (button[i][y].getBackground() == Color.BLACK)
                    {
                        if ((i - x) >= 2)
                        {
                            int count = 0;
                            for (int k = x; k < i; k++)
                            {
                                if (button[k][y].getBackground() == Color.WHITE)
                                    count++;
                            }

                            if (count == (i - x - 1))
                            {
                                for (int k = x; k < i; k++)
                                    button[k][y].setBackground(Color.BLACK);
                            }

                        }

                        if ((x - i) >= 2)
                        {
                            int count = 0;
                            for (int k = x; k > i; k--)
                            {
                                if (button[k][y].getBackground() == Color.WHITE)
                                    count++;
                            }

                            if (count == (x - i - 1))
                            {
                                for (int k = x; k > i; k--)
                                    button[k][y].setBackground(Color.BLACK);
                            }
                        }
                    }
                }

                for (int j = 0; j < 8; j++)
                {
                    if (button[x][j].getBackground() == Color.BLACK)
                    {
                        if ((j - y) >= 2)
                        {
                            int count = 0;
                            for (int k = y; k < j; k++)
                            {
                                if (button[x][k].getBackground() == Color.WHITE)
                                    count++;
                            }

                            if (count == (j - y - 1))
                            {
                                for (int k = y; k < j; k++)
                                    button[x][k].setBackground(Color.BLACK);
                            }
                        }

                        if ((y - j) >= 2)
                        {
                            int count = 0;
                            for (int k = y; k > j; k--)
                            {
                                if (button[x][k].getBackground() == Color.WHITE)
                                    count++;
                            }
                            if (count == (y - j - 1))
                            {
                                for (int k = y; k > j; k--)
                                    button[x][k].setBackground(Color.BLACK);
                            }
                        }
                    }
                }

                for (int i = 0; i < 8; i++)
                {
                    for (int j = 0; j < 8; j++)
                    {
                        if (button[i][j].getBackground() == Color.BLACK)
                        {
                            if ((x - i) == (y - j) && (x - i) >= 2)
                            {
                                int yy = y;
                                int count = 0;
                                for (int k = x; k > i; k--)
                                {
                                    if (button[k][yy].getBackground() == Color.WHITE)
                                    {
                                        count++;
                                    }
                                    yy--;

                                }
                                if (count == (x - i - 1))
                                {
                                    yy = y;
                                    for (int k = x; k > i; k--)
                                    {
                                        button[k][yy].setBackground(Color.BLACK);
                                        yy--;
                                    }
                                }
                            }

                            if ((x - i) == (j - y) && (x - i) >= 2)
                            {
                                int yy = y;
                                int count = 0;
                                for (int k = x; k > i; k--)
                                {
                                    if (button[k][yy].getBackground() == Color.WHITE)
                                    {
                                        count++;
                                    }
                                    yy++;

                                }
                                if (count == (x - i - 1))
                                {
                                    yy = y;
                                    for (int k = x; k > i; k--)
                                    {
                                        button[k][yy].setBackground(Color.BLACK);
                                        yy++;
                                    }
                                }
                            }

                            if ((i - x) == (y - j) && (i - x) >= 2)
                            {
                                int yy = y;
                                int count = 0;
                                for (int k = x; k < i; k++)
                                {
                                    if (button[k][yy].getBackground() == Color.WHITE)
                                    {
                                        count++;
                                    }
                                    yy--;

                                }
                                if (count == (i - x - 1))
                                {
                                    yy = y;
                                    for (int k = x; k < i; k++)
                                    {
                                        button[k][yy].setBackground(Color.BLACK);
                                        yy--;
                                    }
                                }
                            }

                            if ((i - x) == (j - y) && (i - x) >= 2)
                            {
                                int yy = y;
                                int count = 0;
                                for (int k = x; k < i; k++)
                                {
                                    if (button[k][yy].getBackground() == Color.WHITE)
                                    {
                                        count++;
                                    }
                                    yy++;

                                }
                                if (count == (i - x - 1))
                                {
                                    yy = y;
                                    for (int k = x; k < i; k++)
                                    {
                                        button[k][yy].setBackground(Color.BLACK);
                                        yy++;
                                    }
                                }
                            }

                        }
                    }
                }

            }
            else
            {
                for (int i = 0; i < 8; i++)
                {
                    if (button[i][y].getBackground() == Color.WHITE)
                    {
                        if ((i - x) >= 2)
                        {
                            int count = 0;
                            for (int k = x; k < i; k++)
                            {
                                if (button[k][y].getBackground() == Color.BLACK)
                                    count++;
                            }

                            if (count == (i - x - 1))
                            {
                                for (int k = x; k < i; k++)
                                    button[k][y].setBackground(Color.WHITE);
                            }

                        }
                        if ((x - i) >= 2)
                        {
                            int count = 0;
                            for (int k = x; k > i; k--)
                            {
                                if (button[k][y].getBackground() == Color.BLACK)
                                    count++;
                            }

                            if (count == (x - i - 1))
                            {
                                for (int k = x; k > i; k--)
                                    button[k][y].setBackground(Color.WHITE);
                            }
                        }
                    }
                }

                for (int j = 0; j < 8; j++)
                {
                    if (button[x][j].getBackground() == Color.WHITE)
                    {
                        if ((j - y) >= 2)
                        {
                            int count = 0;
                            for (int k = y; k < j; k++)
                            {
                                if (button[x][k].getBackground() == Color.BLACK)
                                    count++;
                            }

                            if (count == (j - y - 1))
                            {
                                for (int k = y; k < j; k++)
                                    button[x][k].setBackground(Color.WHITE);
                            }
                        }
                        if ((y - j) >= 2)
                        {
                            int count = 0;
                            for (int k = y; k > j; k--)
                            {
                                if (button[x][k].getBackground() == Color.BLACK)
                                    count++;
                            }
                            if (count == (y - j - 1))
                            {
                                for (int k = y; k > j; k--)
                                    button[x][k].setBackground(Color.WHITE);
                            }
                        }
                    }
                }

                for (int i = 0; i < 8; i++)
                {
                    for (int j = 0; j < 8; j++)
                    {
                        if (button[i][j].getBackground() == Color.WHITE)
                        {
                            if ((x - i) == (y - j) && (x - i) >= 2)
                            {
                                int yy = y;
                                int count = 0;
                                for (int k = x; k > i; k--)
                                {
                                    if (button[k][yy].getBackground() == Color.BLACK)
                                    {
                                        count++;
                                    }
                                    yy--;
                                }
                                if (count == (x - i - 1))
                                {
                                    yy = y;
                                    for (int k = x; k > i; k--)
                                    {
                                        button[k][yy].setBackground(Color.WHITE);
                                        yy--;
                                    }
                                }
                            }

                            if ((x - i) == (j - y) && (x - i) >= 2)
                            {
                                int yy = y;
                                int count = 0;
                                for (int k = x; k > i; k--)
                                {
                                    if (button[k][yy].getBackground() == Color.BLACK)
                                    {
                                        count++;
                                    }
                                    yy++;

                                }
                                if (count == (x - i - 1))
                                {
                                    yy = y;
                                    for (int k = x; k > i; k--)
                                    {
                                        button[k][yy].setBackground(Color.WHITE);
                                        yy++;
                                    }
                                }
                            }
                            if ((i - x) == (y - j) && (i - x) >= 2)
                            {
                                int yy = y;
                                int count = 0;
                                for (int k = x; k < i; k++)
                                {
                                    if (button[k][yy].getBackground() == Color.BLACK)
                                    {
                                        count++;
                                    }
                                    yy--;

                                }
                                if (count == (i - x - 1))
                                {
                                    yy = y;
                                    for (int k = x; k < i; k++)
                                    {
                                        button[k][yy].setBackground(Color.WHITE);
                                        yy--;
                                    }
                                }
                            }
                            if ((i - x) == (j - y) && (i - x) >= 2)
                            {
                                int yy = y;
                                int count = 0;
                                for (int k = x; k < i; k++)
                                {
                                    if (button[k][yy].getBackground() == Color.BLACK)
                                    {
                                        count++;
                                    }
                                    yy++;

                                }
                                if (count == (i - x - 1))
                                {
                                    yy = y;
                                    for (int k = x; k < i; k++)
                                    {
                                        button[k][yy].setBackground(Color.WHITE);
                                        yy++;

                                    }
                                }
                            }

                        }
                    }
                }

            }

        }
        //判断是否还有位置可以下棋
        private boolean hasValidPosition(){
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    if(isValidPosition(i, j))
                        return true;
                }
            }
            return false;
        }

        //统计黑白棋数目，判断输赢
        private void judgeWinner(){
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    if(button[i][j].getBackground()==Color.BLACK)
                        blackChess++;
                    if(button[i][j].getBackground()==Color.WHITE)
                        whiteChess++;
                }
            }
            if(blackChess>whiteChess)
                JOptionPane.showMessageDialog(null, "黑方获胜！");
            else if(blackChess<whiteChess)
                JOptionPane.showMessageDialog(null, "白方获胜！");
            else
                JOptionPane.showMessageDialog(null, "平局！");
        }

        //交换
        public void changePlayer(){
            if(isBlackPlay==true){
                isBlackPlay=false;
                totalNumber();
                player="黑棋："+blackTotal+"    "+"白棋："+whiteTotal+"  "+"请白色方下棋";
                l1.setText(player);
            }
            else {isBlackPlay=true;
                    totalNumber();
                    player="黑棋："+blackTotal+"    "+"白棋："+whiteTotal+"  "+"请黑色方下棋";
                    l1.setText(player);
            }
        }
        //统计黑白棋数目
        public void totalNumber() {
            blackTotal = 0;
            whiteTotal = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (button[i][j].getBackground() == Color.BLACK)
                        blackTotal++;
                    if (button[i][j].getBackground() == Color.WHITE)
                        whiteTotal++;
                }
            }
        }

        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if(gameover==true){
                JOptionPane.showMessageDialog(null, "棋局结束 请重新开始");
                return;
            }else{
                if(!isValidPosition(row, col)){
                    JOptionPane.showMessageDialog(null, "这是非法位置 请重新放置");
                    return;
                }else{
                    refresh(row, col);
                    changePlayer();
                    if(!hasValidPosition()){
                        changePlayer();
                        {
                            if(!hasValidPosition()){
                                gameover=true;
                                judgeWinner();
                            }else{
                                JOptionPane.showMessageDialog(null, "对手已无处可下 己方继续");
                                return;
                            }
                        }
                    }
                    else
                        return;
                }
            }
        }

        }
    //“退出”按钮
    class renewHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    button[i][j].setBackground(Color.gray);
                }
            }
            button[3][3].setBackground(Color.WHITE);
            button[3][4].setBackground(Color.BLACK);
            button[4][3].setBackground(Color.BLACK);
            button[4][4].setBackground(Color.WHITE);

            isBlackPlay=true;
            blackChess=0;
            whiteChess=0;
        }

    }
    //“重新开始”按钮
    class exitHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            System.exit(0);
        }

    }




    public static void main(String[] args){
        Reversi my_Reversi=new Reversi();
        my_Reversi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        my_Reversi.setSize(700,800);
        my_Reversi.setVisible(true);
    }
}