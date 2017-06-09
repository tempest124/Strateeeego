package com.example.csaper6.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static com.example.csaper6.myapplication.R.layout.activity_main;

public class MainActivity2 extends AppCompatActivity {

    public static final int AMOUNT_OF_SPACES_X = 10, AMOUNT_OF_SPACES_Y = 10;
    public ArrayList<String> pieces;
    public ArrayList<String> pieces2;
    public ArrayList<String> pieces3;
    public ArrayList<Integer> pieceCount;
    private Board board;
    public boolean turn;
    private int position;
    private OutputStream outputStream;
    private InputStream inStream;
    private FloatingActionButton myFab;
    private String[] charSequenceItems;
    private String[] rules;
    public Piece placePiece;
    public int saveSpaceI;
    public int saveSpaceJ;
    public int spaceToGoX;
    public int spaceToGoY;
    private int tester;
    public Space[][] spaces;
    private boolean boardSetUp;
    private boolean fromMenu;
    private Drawable[] images;
    private Toolbar toolbar;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.instructions:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity2.this);
                rules = pieces.toArray(new String[1]);
                rules[0]="Stratego is a game in which you need to capture the flag of your opponent while defending your own flag. To capture the flag you use your army of 40 pieces. Pieces have a rank and represent individual officers and soldiers in an army. In addition to those ranked pieces you can use bombs to protect your flag."+


                        "Pieces move 1 square per turn, horizontally or vertically. Only the scout can move over multiple empty squares per turn. Pieces cannot jump over another piece."+

                        "If a piece is moved onto a square occupied by an opposing piece, their identities are revealed. The weaker piece is removed from the board, and the stronger piece is moved into the place formerly occupied by the weaker piece. If the engaging pieces are of equal rank, they are both removed. Pieces may not move onto a square already occupied by another piece without attacking. Exception to the rule of the higher rank winning is the spy. When the spy attacks the marshal, the spy defeats the higher ranked marshal. However, when the marshal attacks the spy, the spy loses. Bombs lose when they are defused by a miner."+

                        "The bombs and the flag cannot be moved. A bomb defeats every piece that tries to attack it, except the miner. The flag loses from every other piece. When you capture the flag of your opponent you win the game."+

                        "The Stratego board consists of 10 x 10 squares. Within the board there are two obstacles of 2 x 2 squares each. Pieces are not allowed to move there.";
                builder2.setTitle("Instructions")
                        .setItems(rules, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog2, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                if(which == 1)
                                {
                                    dialog2.dismiss();
                                }


                            }
                        });
                final AlertDialog dialog2 = builder2.create();
                dialog2.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ViewGroup layout = (FrameLayout)View.inflate(this, activity_main, null);
        layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                board = new Board(MainActivity2.this, layout, AMOUNT_OF_SPACES_X, AMOUNT_OF_SPACES_Y);
                spaces = board.getSpaces();
                setClicks();
            }
        });

        loadImages();
        tester = 2;

        setContentView(layout);
        pieces= new ArrayList<String>();
        pieces2= new ArrayList<String>();
        pieceCount= new ArrayList<Integer>();

        fillPieceArray();

        Intent i = getIntent();
        Boolean z = i.getBooleanExtra(MainActivity.T, Boolean.TRUE);
Log.wtf("", "LOOK HERE" + z);

        position=0;
        saveSpaceI=11;
        fromMenu = false;
        boardSetUp = false;
        turn=false;



        myFab = (FloatingActionButton)  layout.findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                charSequenceItems = pieces.toArray(new String[pieces.size()]);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
// Add the buttons
                builder.setTitle("Choose A Piece Type")
                        .setItems(charSequenceItems, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item

                                pieceCount.set(which, pieceCount.get(which)-1);
                                fromMenu=true;
                                placePiece= new Piece((Integer.parseInt(pieces2.get(which))), true, null, true, setImages(which));

                                myFab.setEnabled(false);
                                Log.wtf("qwert", ""+placePiece);
                                if(pieceCount.get(which)==0)
                                {

                                    pieceCount.remove(which);
                                    pieces.remove(which);
                                    pieces2.remove(which);
                                    if(pieceCount.size()==0)
                                    {
                                        boardSetUp = true;
                                        myFab.setVisibility(View.GONE);
                                    }
                                }
                            }
                        });
                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

            }



    public void attack(Piece attack, Piece defend)
    {

        defend.setHidden(false);
        if(attack.getPower()==1 && defend.getPower()==10)
        {
            placing(attack, defend.getSpace());
            Toast.makeText(MainActivity2.this, "Spy Defeats Marshal", Toast.LENGTH_SHORT).show();
        }
        else if(attack.getPower()==3 && defend.getPower()==11)
        {
            placing(attack, defend.getSpace());
            Toast.makeText(MainActivity2.this, "Miner Defeats Bomb", Toast.LENGTH_SHORT).show();
        }
        else if(attack.getPower() > defend.getPower())
        {

            Toast.makeText(MainActivity2.this, ""+pieces3.get((attack.getPower())) + " Defeats " + pieces3.get((defend.getPower())), Toast.LENGTH_SHORT).show();
            placing(attack, defend.getSpace());

        }
        else if(attack.getPower() < defend.getPower())
        {

            Toast.makeText(MainActivity2.this, ""+pieces3.get((defend.getPower())) + " Defeats " + pieces3.get((attack.getPower())), Toast.LENGTH_SHORT).show();
            spaces[saveSpaceI][saveSpaceJ].setPiece(null);
            spaces[saveSpaceI][saveSpaceJ].getButton().setImageDrawable(null);

        }
        else if(attack.getPower() == defend.getPower())
        {

            Toast.makeText(MainActivity2.this, ""+pieces3.get((defend.getPower())) + " Ensures Mutually Assured Destruction With " + pieces3.get((attack.getPower())), Toast.LENGTH_SHORT).show();
            spaces[saveSpaceI][saveSpaceJ].setPiece(null);
            spaces[saveSpaceI][saveSpaceJ].getButton().setImageDrawable(null);
            spaces[spaceToGoX][spaceToGoY].setPiece(null);
            spaces[spaceToGoX][spaceToGoY].getButton().setImageDrawable(null);

        }

    }


    private void loadImages() {
        images = new Drawable[12];
        images[0] = this.getResources().getDrawable(R.drawable.mudkiptwo);
        images[2] = this.getResources().getDrawable(R.drawable.two);
        images[3] = this.getResources().getDrawable(R.drawable.three);
        images[4] = this.getResources().getDrawable(R.drawable.four);
        images[5] = this.getResources().getDrawable(R.drawable.five);
        images[6] = this.getResources().getDrawable(R.drawable.six);
        images[7] = this.getResources().getDrawable(R.drawable.seven);
        images[8] = this.getResources().getDrawable(R.drawable.eight);
        images[9] = this.getResources().getDrawable(R.drawable.nine);
        images[10] = this.getResources().getDrawable(R.drawable.ten);
        images[1] = this.getResources().getDrawable(R.drawable.spy);
        images[11] = this.getResources().getDrawable(R.drawable.bomb);
    }


    public Drawable setImages(int i)
    {
        if((Integer.parseInt(pieces2.get(i))==0))
            return  images[0];
        else if((Integer.parseInt(pieces2.get(i))==1))
            return  images[1];
        else if((Integer.parseInt(pieces2.get(i))==2))
            return  images[2];
        else if((Integer.parseInt(pieces2.get(i))==3))
            return  images[3];
        else if((Integer.parseInt(pieces2.get(i))==4))
            return  images[4];
        else if((Integer.parseInt(pieces2.get(i))==5))
            return  images[5];
        else if((Integer.parseInt(pieces2.get(i))==6))
            return  images[6];
        else if((Integer.parseInt(pieces2.get(i))==7))
            return  images[7];
        else if((Integer.parseInt(pieces2.get(i))==8))
            return  images[8];
        else if((Integer.parseInt(pieces2.get(i))==9))
            return  images[9];
        else if((Integer.parseInt(pieces2.get(i))==10))
            return  images[10];
        else if((Integer.parseInt(pieces2.get(i))==11))
            return  images[11];
        return this.getResources().getDrawable(R.drawable.mudkip);
    }

    public boolean validMove(Piece piece, Space space)
    {
        if(fromMenu) {
            fromMenu = false;
            return true;
        }
        else {
            if (piece.getPower() > 10 || piece.getPower() < 1) {
                Toast.makeText(MainActivity2.this, "Invalid move", Toast.LENGTH_SHORT).show();
                return false;
            } else if (piece.getSpace().getX() == space.getX() && piece.getSpace().getY() == space.getY()) {
                Toast.makeText(MainActivity2.this, "Piece Unclicked", Toast.LENGTH_SHORT).show();
                return false;
            } else if (piece.getPower() == 2 && (piece.getSpace().getX() == space.getX() || piece.getSpace().getY() == space.getY()))
                return true;
            else if ((piece.getSpace().getX() == space.getX() + 1 && piece.getSpace().getY() == space.getY()) ||
                    (piece.getSpace().getX() == space.getX() - 1 && piece.getSpace().getY() == space.getY()) ||
                    (piece.getSpace().getX() == space.getX() && piece.getSpace().getY() == space.getY() + 1) ||
                    (piece.getSpace().getX() == space.getX() && piece.getSpace().getY() == space.getY() - 1))
                return true;


            Toast.makeText(MainActivity2.this, "Invalid move2", Toast.LENGTH_SHORT).show();
            return false;
        }

    }


    public void fillPieceArray()
    {
//        Piece two = new Piece(2, true, false, false, false, null, false);
//        Piece three = new Piece(3, false, true, false, false, null, false);
//        Piece four = new Piece(4, false, false, false, false, null, false);
//        Piece five = new Piece(5, false, false, false, false, null, false);
//        Piece six = new Piece(6, false, false, false, false, null, false);
//        Piece seven = new Piece(7, false, false, false, false, null, false);
//        Piece eight = new Piece(8, false, false, false, false, null, false);
//        Piece nine = new Piece(9, false, false, false, false, null, false);
//        Piece ten = new Piece(10, false, false, false, false, null, false);
//        Piece spy = new Piece(1, false, false, true, false, null, false);
//        Piece bomb = new Piece(11, false, false, false, false, null, false);
//        Piece flag = new Piece(0, false, false, false, false, null, false);
//        pieces.add(0, two);
//        pieces.add(0, three);
//        pieces.add(0, four);
//        pieces.add(0, five);
//        pieces.add(0, six);
//        pieces.add(0, seven);
//        pieces.add(0, eight);
//        pieces.add(0, nine);
//        pieces.add(0, ten);
//        pieces.add(0, spy);
//        pieces.add(0, bomb);
//        pieces.add(0, flag);

        pieces.add(0, "Flag");
        pieces.add(1, "Spy");
        pieces.add(2, "Scout");
        pieces.add(3, "Miner");
        pieces.add(4, "Sergeant");
        pieces.add(5, "Lieutenant");
        pieces.add(6, "Captain");
        pieces.add(7, "Major");
        pieces.add(8, "Colonel");
        pieces.add(9, "General");
        pieces.add(10, "Marshal");
        pieces.add(11, "Bomb");

        pieces2.add(0, "0");
        pieces2.add(1, "1");
        pieces2.add(2, "2");
        pieces2.add(3, "3");
        pieces2.add(4, "4");
        pieces2.add(5, "5");
        pieces2.add(6, "6");
        pieces2.add(7, "7");
        pieces2.add(8, "8");
        pieces2.add(9, "9");
        pieces2.add(10, "10");
        pieces2.add(11, "11");

        pieces3.add(0, "Flag");
        pieces3.add(1, "Spy");
        pieces3.add(2, "Scout");
        pieces3.add(3, "Miner");
        pieces3.add(4, "Sergeant");
        pieces3.add(5, "Lieutenant");
        pieces3.add(6, "Captain");
        pieces3.add(7, "Major");
        pieces3.add(8, "Colonel");
        pieces3.add(9, "General");
        pieces3.add(10, "Marshal");
        pieces3.add(11, "Bomb");

        pieceCount.add(0, 1);
        pieceCount.add(1, 1);
        pieceCount.add(2, 8);
        pieceCount.add(3, 5);
        pieceCount.add(4, 4);
        pieceCount.add(5, 4);
        pieceCount.add(6, 4);
        pieceCount.add(7, 3);
        pieceCount.add(8, 2);
        pieceCount.add(9, 1);
        pieceCount.add(10, 1);
        pieceCount.add(11, 6);

    }
    public void placing(Piece p, Space s)
    {
        if(saveSpaceI!=11) {
            Log.wtf("qwert", "kek2?" + saveSpaceI + " " + saveSpaceJ);
            spaces[saveSpaceI][saveSpaceJ].setPiece(null);
            spaces[saveSpaceI][saveSpaceJ].getButton().setImageDrawable(null);
        }
        s.setPiece(p);
        p.setSpace(s);
        //spaces[ii][jj].getButton().setBackgroundColor(Color.TRANSPARENT);
        s.getButton().setImageDrawable(s.getPiece().getImage());
//                            spaces[ii][jj].getPiece().getImage().set
        placePiece=null;
    }
    public void setClicks() {
        //if (turn) {
            for (int i = 0; i < AMOUNT_OF_SPACES_Y; i++) {
                for (int j = 0; j < AMOUNT_OF_SPACES_X; j++) {
                    Log.wtf("qwert", spaces[i][j].getButton().getX() + ", " + spaces[i][j].getButton().getY());
                    final int ii = i, jj = j;
                    spaces[i][j].getButton().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.wtf("qwert", "kek?");
                            if (boardSetUp) {
                                if (placePiece == null && spaces[ii][jj].getPiece() != null) {
                                    saveSpaceI = ii;
                                    saveSpaceJ = jj;
                                    placePiece = spaces[ii][jj].getPiece();
                                    Log.wtf("qwert", "" + placePiece);
                                } else if (placePiece != null) {
                                    spaceToGoX = ii;
                                    spaceToGoY = jj;
                                    if (validMove(placePiece, spaces[spaceToGoX][spaceToGoY])) {
                                        if (spaces[ii][jj].getPiece() == null) {
                                            placing(placePiece, spaces[spaceToGoX][spaceToGoY]);
                                        } else {
                                            Log.wtf("qwert", "" + spaces[ii][jj].getPiece());
                                            attack(placePiece, spaces[spaceToGoX][spaceToGoY].getPiece());
                                        }
                                    }
                                    placePiece = null;
                                }
                            } else if (placePiece != null) {

                                if (!boardSetUp) {
                                    if (jj > 5) {
                                        placing(placePiece, spaces[ii][jj]);
                                        myFab.setEnabled(true);
                                        if (tester == 0)
                                            boardSetUp = true;
                                        tester--;

                                    }

                                }

                            }
                        }
                    });
                }
            }
        }
//        else {
//            Toast.makeText(MainActivity2.this, "Not Your Turn", Toast.LENGTH_SHORT).show();
//        }
//    }


}



