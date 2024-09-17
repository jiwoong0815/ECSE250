package assignment3;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

public class Block {
 private int xCoord;
 private int yCoord;
 private int size; // height/width of the square
 private int level; // the root (outer most block) is at level 0
 private int maxDepth; 
 private Color color;

 private Block[] children; // {UR, UL, LL, LR}

 public static Random gen = new Random();
 
 
 /*
  * These two constructors are here for testing purposes. 
  */
 public Block() {}
 
 public Block(int x, int y, int size, int lvl, int  maxD, Color c, Block[] subBlocks) {
  this.xCoord=x;
  this.yCoord=y;
  this.size=size;
  this.level=lvl;
  this.maxDepth = maxD;
  this.color=c;
  this.children = subBlocks;
 }
 
 

 /*
  * Creates a random block given its level and a max depth. 
  * 
  * xCoord, yCoord, size, and highlighted should not be initialized
  * (i.e. they will all be initialized by default)
  */
 public Block(int lvl, int maxDepth) {
  if (lvl > maxDepth) {
   throw new IllegalArgumentException("level should be always smaller than or equal to max Depth");
  }
  this.level = lvl;
  this.maxDepth = maxDepth;

  if (lvl+1 <= maxDepth) {
   double RandNum = gen.nextDouble();
   if (RandNum < Math.exp(-0.25 * lvl)) {
    this.color = null;
    this.children = new Block[]{new Block(lvl + 1, maxDepth), new Block(lvl + 1, maxDepth), new Block(lvl + 1, maxDepth), new Block(lvl + 1, maxDepth)};
   } else {
    int RandInt = gen.nextInt(4);
    this.color = GameColors.BLOCK_COLORS[RandInt];
    this.children = new Block[0];
   }
  } else {
   int RandInt = gen.nextInt(4);
   this.color = GameColors.BLOCK_COLORS[RandInt];
   this.children = new Block[0];
  }
 }


 /*
  * Updates size and position for the block and all of its sub-blocks, while
  * ensuring consistency between the attributes and the relationship of the
  * blocks.
  *
  *  The size is the height and width of the block. (xCoord, yCoord) are the
  *  coordinates of the top left corner of the block.
  */
 public void updateSizeAndPosition (int size, int xCoord, int yCoord) {
  if (size <= 0 ){
   throw new IllegalArgumentException ("size is invalid");
  }
  this.size = size;
  this.xCoord = xCoord;
  this.yCoord = yCoord;

  if (this.color == null){
   if(size%2 !=0){
    throw new IllegalArgumentException ("size is not divisible by 2");
   }
   this.children[0].updateSizeAndPosition(size/2,xCoord+(size/2),yCoord);
   this.children[1].updateSizeAndPosition(size/2,xCoord,yCoord);
   this.children[2].updateSizeAndPosition(size/2,xCoord,yCoord+(size/2));
   this.children[3].updateSizeAndPosition(size/2,xCoord+(size/2),yCoord+(size/2));
  }
 }


 /*
  * Returns a List of blocks to be drawn to get a graphical representation of this block.
  *
  * This includes, for each undivided Block:
  * - one BlockToDraw in the color of the block
  * - another one in the FRAME_COLOR and stroke thickness 3
  *
  * Note that a stroke thickness equal to 0 indicates that the block should be filled with its color.
  *
  * The order in which the blocks to draw appear in the list does NOT matter.
  */
 public ArrayList<BlockToDraw> getBlocksToDraw() {

  ArrayList<BlockToDraw> list = new ArrayList<BlockToDraw>();
  if (this.color == null){
    for (int i = 0; i < this.children.length; i++){
     ArrayList<BlockToDraw> data = this.children[i].getBlocksToDraw();
     list.addAll(data);
    }
  }
  else{
   list.add(new BlockToDraw(this.color,this.xCoord,this.yCoord,this.size,0));
   list.add(new BlockToDraw(GameColors.FRAME_COLOR,this.xCoord,this.yCoord,this.size,3));
  }
  return list;
 }

 /*
  * This method is provided and you should NOT modify it.
  */
 public BlockToDraw getHighlightedFrame() {
  return new BlockToDraw(GameColors.HIGHLIGHT_COLOR, this.xCoord, this.yCoord, this.size, 5);
 }



 /*
  * Return the Block within this Block that includes the given location
  * and is at the given level. If the level specified is lower than
  * the lowest block at the specified location, then return the block
  * at the location with the closest level value.
  *
  * The location is specified by its (x, y) coordinates. The lvl indicates
  * the level of the desired Block. Note that if a Block includes the location
  * (x, y), and that Block is subdivided, then one of its sub-Blocks will
  * contain the location (x, y) too. This is why we need lvl to identify
  * which Block should be returned.
  *
  * Input validation:
  * - this.level <= lvl <= maxDepth (if not throw exception)
  * - if (x,y) is not within this Block, return null.
  */
 public Block getSelectedBlock(int x, int y, int lvl) {
  if (lvl < this.level || lvl > this.maxDepth){
   throw new IllegalArgumentException("invalid level input");
  }
  if(this.is_inRange(x,y)  && this.level < lvl && this.color == null){
   int whichChild = 0; // checking where x and y are located among it's child
   if(this.children[0].is_inRange(x,y)){
    whichChild = 0;
   }
   else if(this.children[1].is_inRange(x,y)){
    whichChild = 1;
   }
   else if(this.children[2].is_inRange(x,y)){
    whichChild = 2;
   }
   else if(this.children[3].is_inRange(x,y)){
    whichChild = 3;
   }
   Block sel = this.children[whichChild].getSelectedBlock(x,y,lvl);
   return sel;
  }
  else if (this.is_inRange(x,y) && this.level <= lvl){
   return this;
  }
  return null;
 }

 private boolean is_inRange(int x ,int y){
  int Xrange_r = xCoord + this.size;
  int Xrange_l = xCoord;
  int Yrange_u = yCoord;
  int Yrange_d = yCoord + this.size;
  return (Xrange_l<=x && x<Xrange_r && Yrange_u <= y && y < Yrange_d);
 }
 
 

 /*
  * Swaps the child Blocks of this Block. 
  * If input is 1, swap vertically. If 0, swap horizontally. 
  * If this Block has no children, do nothing. The swap 
  * should be propagate, effectively implementing a reflection
  * over the x-axis or over the y-axis.
  * 
  */
 public void reflect(int direction) {
  if (direction != 0 && direction != 1){
   throw new IllegalArgumentException("invalid input");
  }

  if (direction == 0){ //horiznotal reflect
   if(this.color == null){
    // changing 3 and 0
    int xCoord3_h = this.children[3].xCoord;
    int yCoord3_h = this.children[3].yCoord;
    this.children[3].updateSizeAndPosition(this.children[3].size,this.children[0].xCoord,this.children[0].yCoord);
    this.children[0].updateSizeAndPosition(this.children[0].size,xCoord3_h,yCoord3_h);
    Block temp0_h = this.children[0];
    this.children[0] = this.children[3];
    this.children[3] = temp0_h;

    // changing 2 and 1
    int xCoord2_h = this.children[2].xCoord;
    int yCoord2_h = this.children[2].yCoord;
    this.children[2].updateSizeAndPosition(this.children[2].size,this.children[1].xCoord,this.children[1].yCoord);
    this.children[1].updateSizeAndPosition(this.children[1].size,xCoord2_h,yCoord2_h);
    Block temp1_h = this.children[1];
    this.children[1] = this.children[2];
    this.children[2] = temp1_h;

    // repeat to it's children
    for (int i = 0; i < this.children.length; i++){
     this.children[i].reflect(direction);
    }
   }
  }
  else{ // vertical
   if(this.color == null){
    // changing 1 and 0
    int xCoord1_v = this.children[1].xCoord;
    int yCoord1_v = this.children[1].yCoord;
    this.children[1].updateSizeAndPosition(this.children[1].size,this.children[0].xCoord,this.children[0].yCoord);
    this.children[0].updateSizeAndPosition(this.children[0].size,xCoord1_v,yCoord1_v);
    Block temp0_v = this.children[0];
    this.children[0] = this.children[1];
    this.children[1] = temp0_v;

    // changing 2 and 3
    int xCoord2_v = this.children[2].xCoord;
    int yCoord2_v = this.children[2].yCoord;
    this.children[2].updateSizeAndPosition(this.children[2].size,this.children[3].xCoord,this.children[3].yCoord);
    this.children[3].updateSizeAndPosition(this.children[3].size,xCoord2_v,yCoord2_v);
    Block temp3_v = this.children[3];
    this.children[3] = this.children[2];
    this.children[2] = temp3_v;

    // repeat to it's children
    for (int i = 0; i < this.children.length; i++){
     this.children[i].reflect(direction);
    }
   }
  }
 }
 

 
 /*
  * Rotate this Block and all its descendants. 
  * If the input is 1, rotate clockwise. If 0, rotate 
  * counterclockwise. If this Block has no children, do nothing.
  */
 public void rotate(int direction) {
  if (direction != 0 && direction != 1){
   throw new IllegalArgumentException("invalid input");
  }

  if (direction == 1){
   if(this.color == null){
    this.changeWith(this.children[0],this.children[3],0,3);
    this.changeWith(this.children[0],this.children[1],0,1);
    this.changeWith(this.children[1],this.children[2],1,2);

    for (int i = 0; i < this.children.length; i++) {
     this.children[i].rotate(direction);
    }
   }
  }
  else {
   if (this.color == null) {
    this.changeWith(this.children[0], this.children[1], 0, 1);
    this.changeWith(this.children[2], this.children[3], 2, 3);
    this.changeWith(this.children[0], this.children[2], 0, 2);

    for (int i = 0; i < this.children.length; i++) {
     this.children[i].rotate(direction);
   }
   }
  }
 }

 private void changeWith(Block from, Block other, int fromNum, int otherNum){ // new function to help rotation
  Block tempB = this.children[fromNum];
  int tempX = other.xCoord;
  int tempY = other.yCoord;
  other.updateSizeAndPosition(other.size, from.xCoord,from.yCoord);
  from.updateSizeAndPosition(from.size,tempX,tempY);
  this.children[fromNum] = this.children[otherNum];
  this.children[otherNum] = tempB;
 }


 /*
  * Smash this Block.
  * 
  * If this Block can be smashed,
  * randomly generate four new children Blocks for it.  
  * (If it already had children Blocks, discard them.)
  * Ensure that the invariants of the Blocks remain satisfied.
  * 
  * A Block can be smashed iff it is not the top-level Block 
  * and it is not already at the level of the maximum depth.
  * 
  * Return True if this Block was smashed and False otherwise.
  * 
  */
 public boolean smash() {
  if (this.level != 0 && this.level < this.maxDepth) {
   this.color = null;
   this.children = new Block[]{new Block(this.level + 1, maxDepth), new Block(this.level + 1, maxDepth), new Block(this.level + 1, maxDepth), new Block(this.level + 1, maxDepth)};
   this.updateSizeAndPosition(this.size,this.xCoord,this.yCoord);
   return true;
  }
  else {
   return false;
  }
 }
 
 
 /*
  * Return a two-dimensional array representing this Block as rows and columns of unit cells.
  * 
  * Return and array arr where, arr[i] represents the unit cells in row i, 
  * arr[i][j] is the color of unit cell in row i and column j.
  * 
  * arr[0][0] is the color of the unit cell in the upper left corner of this Block.
  */
 public Color[][] flatten() {

  int flattenSize = (int) Math.pow(2,this.maxDepth-this.level);

  Color[][] array = new Color[flattenSize][flattenSize];
  if (this.color != null){ //when the block don't have children
   for (int i = 0; i < flattenSize; i++){
    for(int j = 0; j < flattenSize; j++){
     array[i][j] = this.color;
    }
   }
  }
  else{
   Color[][][] children_arrays = new Color[4][][];
   for (int i = 0 ; i < 4; i++){
    children_arrays[i] = this.children[i].flatten();
   }
   for (int i = 0; i < flattenSize; i++) {
    for (int j = 0; j < flattenSize; j++) {
     if( i < (flattenSize/2) && j >= (flattenSize/2)){ // location at children 0
       array[i][j] = children_arrays[0][i][j - (flattenSize/2)];
     }
     else if (i < (flattenSize/2) && j < (flattenSize/2)) { // children 1
      array[i][j] = children_arrays[1][i][j];
     }
     else if (i >= (flattenSize/2) && j < (flattenSize/2)) { // children 2
      array[i][j] = children_arrays[2][i - (flattenSize/2)][j];
     }
     else if (i >= (flattenSize/2) && j >= (flattenSize/2)) { // children 3
      array[i][j] = children_arrays[3][i - (flattenSize/2)][j - (flattenSize/2)];
     }
    }
   }
  }
  return array;
 }

 
 
 // These two get methods have been provided. Do NOT modify them. 
 public int getMaxDepth() {
  return this.maxDepth;
 }
 
 public int getLevel() {
  return this.level;
 }


 /*
  * The next 5 methods are needed to get a text representation of a block. 
  * You can use them for debugging. You can modify these methods if you wish.
  */
 public String toString() {
  return String.format("pos=(%d,%d), size=%d, level=%d"
    , this.xCoord, this.yCoord, this.size, this.level);
 }

 public void printBlock() {
  this.printBlockIndented(0);
 }

 private void printBlockIndented(int indentation) {
  String indent = "";
  for (int i=0; i<indentation; i++) {
   indent += "\t";
  }

  if (this.children.length == 0) {
   // it's a leaf. Print the color!
   String colorInfo = GameColors.colorToString(this.color) + ", ";
   System.out.println(indent + colorInfo + this);   
  } else {
   System.out.println(indent + this);
   for (Block b : this.children)
    b.printBlockIndented(indentation + 1);
  }
 }
 
 private static void coloredPrint(String message, Color color) {
  System.out.print(GameColors.colorToANSIColor(color));
  System.out.print(message);
  System.out.print(GameColors.colorToANSIColor(Color.WHITE));
 }

 public void printColoredBlock(){
  Color[][] colorArray = this.flatten();
  for (Color[] colors : colorArray) {
   for (Color value : colors) {
    String colorName = GameColors.colorToString(value).toUpperCase();
    if(colorName.length() == 0){
     colorName = "\u2588";
    }else{
     colorName = colorName.substring(0, 1);
    }
    coloredPrint(colorName, value);
   }
   System.out.println();
  }
 }
 
}
