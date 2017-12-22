// Percolation Java API 
// Implementing weighted quick union find 
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
// grid size n x n 
private int n; 
//unionfind data struct 
private WeightedQuickUnionUF unionFind; 
// open sites identification 
// 0 closed, 1 open, 2 open and connected to bottom 
private byte[][] sites; 
// number of open sites 
private int openSites; 

// percolation grid n by n with all closed
public Percolation(int n){
 if (n <= 0) { throw new IllegalArgumentException("Invalid input n, n must be > 0");}
 this.n = n + 1; 
 unionFind = new WeightedQuickUnionUF(this.n * this.n); 
 sites = new byte[this.n][this.n];
 }

public static void main(String[] args) {
 In input1 = new In(args[0]); 
 int n = input1.readInt(); 
 Percolation perc = new Percolation(n); 
 boolean percolates = false; 
 int counter = 0; 
 while (!input1.isEmpty()) {
  int row = input1.readInt(); 
  int col = input1.readInt(); 
  if(!perc.isOpen(row, col)) {
   counter++; 
  }
  perc.open(row, col); 
  percolates = perc.percolates(); 
  if (percolates) {
   break; 
  }
 }
 StdOut.println(counter + " opened sites"); 
 if (percolates) {StdOut.println("percolated");}
 else {StdOut.println("didn't percolate");}
}

private void isValid(int row, int col) {
 if (row <= 0 || row >= n) { throw new IndexOutOfBoundsException("Invalid row input");}
 if (col <= 0 || col >= n) { throw new IndexOutOfBoundsException("Invalid col input");}
}

public void open(int row, int col) {
 isValid(row, col); 
 if (isOpen(row, col)) {
  return;
 }
 sites[row][col] = 1; 
 openSites++; 
 // bottom
 if (row == n-1) sites[row][col] = 2; 
 // top
 if (row == 1) {
  unionFind.union(0, row * n + col); 
  // 1 x 1 grid 
  if (sites[row][col] == 2) {sites[0][0] = 2;} 
 }
 // connect above 
 if (row - 1 > 0 && isOpen(row-1, col)) {
  update(row - 1, col, row, col);
 }
 // connect below
 if (row + 1 < n && isOpen(row + 1, col)) {
  update(row+ 1, col, row, col);
 }
 // connect left 
 if (col - 1 > 0 && isOpen(row, col-1)) {
  update(row, col-1, row, col); 
 }
 // connect right 
 if (col + 1 < n && isOpen(row, col+1)) {
  update(row, col+1, row, col); 
 }
}

private void update(int row1, int col1, int row2, int col2) {
 int point1 = unionFind.find(row1*n + col1); 
 int point2 = unionFind.find(row2*n + col2); 
 unionFind.union(row1*n + col1, row2*n + col2); 
 // if bottom connection update connected site 
 if (sites[point1/n][point1%n] == 2 || sites[point2/n][point2%n] == 2) {
  int point3 = unionFind.find(row2*n + col2);
  sites[point3/n][point3%n] =2; 
 }
}

public boolean isOpen(int row, int col) {
  isValid(row, col); 
  return sites[row][col] > 0; 
 }

public boolean isFull(int row, int col) {
 isValid(row, col); 
 return (sites[row][col] > 0 && unionFind.connected(0, row*n + col));
}

public int numberOfOpenSites() {
 return openSites; 
}

public boolean percolates() {
 int root = unionFind.find(0); 
 return sites[root/n][root%n] == 2; 
}

}
 