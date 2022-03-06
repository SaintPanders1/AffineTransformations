package Cube;

import LineBase.Lines;
import LineBase.LineBase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.Scanner;

public class CubeMain {

    //faster version of Matrix Multiplication
    public static double[][] WinogradMethod(double[][] matrix1, double[][] matrix2) throws IllegalArgumentException
    {
        if (matrix1[0].length != matrix2.length)
        {
            throw new IllegalArgumentException("Matrices are not compatible");
        }
        double[][] result = new double[matrix1.length][matrix2[0].length];
        int b = matrix1[0].length;
        int d = b/2;
        double[] rowFactor = new double[matrix1.length];
        double[] columnFactor = new double[matrix2[0].length];
        for(int i = 0; i < matrix1.length; i++)
        {
            rowFactor[i] = (matrix1[i][0] * matrix1[i][1]);
            for(int j = 1; j < d; j++)
            {
                rowFactor[i] += matrix1[i][2 * j] * matrix1[i][2 * j + 1];
            }
        }

        for(int i = 0; i < matrix2[0].length; i++)
        {
            columnFactor[i] = matrix2[0][i] * matrix2[1][i];
            for(int j =1; j < d; j++)
            {
                columnFactor[i] += matrix2[2*j][i] * matrix2[2*j+1][i];
            }
        }

        for(int i = 0; i < rowFactor.length; i++)
        {
            for(int j = 0; j < columnFactor.length; j++)
            {
                result[i][j] = -rowFactor[i] - columnFactor[j];
                for(int k = 0; k < d; k++)
                {
                    result[i][j] = result[i][j] + (matrix1[i][2*k] + matrix2[2*k+1][j]) * (matrix1[i][2*k+1] + matrix2[2*k][j]);
                }
            }
        }

        if(2 * (matrix1[0].length /2) != matrix1[0].length)
        {
            for(int i = 0; i < matrix1.length; i++)
            {
                for(int j = 0; j < matrix2[0].length; j++)
                {
                    result[i][j] = result[i][j] + matrix1[i][b] * matrix2[b][j];
                }
            }
        }
        return result;
    }

    public static double[][] MatrixMultiplication(double[][] matrix1, double[][] matrix2)throws IllegalArgumentException
    {
        if(matrix1[0].length != matrix2.length)
        {
            throw new IllegalArgumentException("Matrices do not match");
        }
        double[][] result = new double[matrix1.length][matrix2[0].length];
        for(int i = 0; i < matrix2.length; i++)
        {
            for(int j = 0; j <matrix2[0].length; j++ )
            {
                for(int k = 0; k < matrix1[0].length; k++)
                {
                    result[i][j] = result[i][j] + matrix1[j][k] * matrix2[i][j];
                }
            }
        }
        return result;
    }

    public static double[][] scale(double x, double y, double z)
    {
        return new double[][]  {{x,0.0,0.0,0.0},{0.0,y,0.0,0.0}, {0.0,0.0,z,0.0}, {0.0,0.0,0.0,1.0}};
    }
    public static double[][] translate(double x, double y, double z)
    {
        return new double[][] {{1.0,0.0,0.0,x},{0.0,1.0,0.0,y}, {0.0,0.0,1.0,z}, {0.0,0.0,0.0,1.0}};
    }
    public static double[][] rotateX(double angle)
    {
        angle = Math.toRadians(angle);
        return new double[][] {{1.0,0.0,0.0,0.0},{0.0,Math.cos(angle),-Math.sin(angle),0.0}, {0.0,Math.sin(angle),Math.cos(angle),0.0}, {0.0,0.0,0.0,1.0}};
    }
    public static double[][] rotateY(double angle)
    {
        angle = Math.toRadians(angle);
        return new double[][] {{Math.cos(angle),0.0,Math.sin(angle),0.0},{0.0,1.0,0.0,0.0}, {-Math.sin(angle),0.0,Math.cos(angle),0.0}, {0.0,0.0,0.0,1.0}};
    }
    public static double[][] rotateZ(double angle)
    {
        angle = Math.toRadians(angle);
        return new double[][] {{Math.cos(angle),-Math.sin(angle),0.0,0.0},{Math.sin(angle),Math.cos(angle),0.0,0.0}, {0.0,0.0,1.0,0.0}, {0.0,0.0,0.0,1.0}};
    }


    public static void main(String [] args) throws FileNotFoundException
    {

        File file = new File("C:\\Users\\andre\\IdeaProjects\\AffineTransformations\\src\\Cube\\cube.txt");
        Scanner scan = new Scanner(file);
        String line = scan.nextLine();
        int numVertices = Integer.parseInt(line.split(" ")[1]);
        double[][] vertices = new double[4][numVertices];
        for(int i = 0; i < numVertices; i++)
        {
            String[] in = scan.nextLine().split(",");
            vertices[0][i] = Double.parseDouble(in[0]);
            vertices[1][i] = Double.parseDouble(in[1]);
            vertices[2][i] = Double.parseDouble(in[2]);
            vertices[3][i] = 1.0;
        }
        line = scan.nextLine();
        int numEdges = Integer.parseInt(line.split(" ")[1]);
        Integer[][] edges = new Integer[2][numEdges];
        for(int i = 0; i < numEdges; i++)
        {
            String[] in = scan.nextLine().split(", ");
            edges[0][i] = Integer.parseInt(in[0]);
            edges[1][i] = Integer.parseInt(in[1]);

        }
//        for(int i = 0; i < edges.length; i++)
//        {
//            for(int j = 0; j < edges[i].length; j++)
//            {
//                System.out.print(edges[i][j]);
//
//            }
//        }
//        for(int i = 0; i < vertices.length; i++)
//        {
//            for(int j = 0; j < vertices[i].length; j++)
//            {
//                System.out.print(vertices[i][j] + ", ");
//            }
//            System.out.println();
//        }
        LineBase lb = new Lines();

        int framebuffer[][][] = new int[3][500][500];

        LineBase.RGBColor[] colorz = new LineBase.RGBColor[12];
        colorz[0] = new LineBase.RGBColor(255, 0, 0);
        colorz[1] = new LineBase.RGBColor(255, 255, 255);
        colorz[2]= new LineBase.RGBColor(155, 255, 0);
        colorz[3] = new LineBase.RGBColor(255, 155, 0);
        colorz[4] = new LineBase.RGBColor(255, 255, 0);
        colorz[5] = new LineBase.RGBColor(255, 51, 51);
        colorz[6] = new LineBase.RGBColor(204, 204, 255);
        colorz[7] = new LineBase.RGBColor(0, 255, 0);
        colorz[8] = new LineBase.RGBColor(0, 204, 0);
        colorz[9] = new LineBase.RGBColor(0, 0, 204);
        colorz[10] = new LineBase.RGBColor(255, 255, 0);
        colorz[11] = new LineBase.RGBColor(255, 0, 127);

        double[][] modificationMatrix;
        double[][] temp;
        double[][] scaleCube = scale(100,100,100);
        double[][] rotateCubeX = rotateX(30);
        double [][] rotateCubeY = rotateY(45);
        double[][] rotateCubeZ = rotateZ(60);
        double[][] translation = translate(200,200,0);

        System.out.println(Arrays.deepToString(vertices) + "\n");

        temp = WinogradMethod(scaleCube, vertices);

        System.out.println(Arrays.deepToString(temp) + "\n");

        modificationMatrix = WinogradMethod(rotateCubeX,scaleCube);

        System.out.println(Arrays.deepToString(scaleCube) + "\n");
        System.out.println(Arrays.deepToString(modificationMatrix));

        temp = modificationMatrix;
        modificationMatrix = WinogradMethod(rotateCubeY,temp);
        temp = modificationMatrix;
        modificationMatrix = WinogradMethod(rotateCubeZ,temp);
        temp = modificationMatrix;
        modificationMatrix = WinogradMethod(translation, temp);
        double[][] result = WinogradMethod(modificationMatrix, vertices);

        for(int i = 0; i < result.length;i++)
        {
            for(int j = 0; j < result[0].length;j++)
            {
                System.out.print(result[i][j] + ", ");
            }
            System.out.println();
        }

        try {
            for(int i = 0; i < edges[0].length; i++) {
                lb.BresenhamFormRGB((int)result[0][edges[0][i]],(int)result[1][edges[0][i]],(int)result[0][edges[1][i]],(int)result[1][edges[1][i]],framebuffer,colorz[i],colorz[i]);
            }
            LineBase.ImageWriteRGB(framebuffer, "cube.png");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }



}

