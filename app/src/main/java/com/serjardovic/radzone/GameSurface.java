package com.serjardovic.radzone;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;
    private MainCharacter mainCharacter;
    private Button b_left, b_up, b_right, b_down, b_talk;
    private InterfaceElement terminal;

    private final List<NPCharacter> npcList = new ArrayList<>();
    private final List<TerrainElement> walkableTerrainList = new ArrayList<>();
    private final List<TerrainElement> unwalkableTerrainList = new ArrayList<>();
    private final List<InterfaceElement> interfaceList = new ArrayList<>();
    private final List<PopupElement> popupList = new ArrayList<>();

    private int faceBitmap = R.drawable.gopnik_face;

    boolean b_talkActive = false;
    boolean popupActive = false;

    String[][] talkMatrixCurrent;
    int[][] screenMatrixCurrent = ScreenMatrix.screenMatrix4;
    int[][] currentDialogueMatrix;
    int[] dialogueMatrix;

    String terminalText = "...", terminalText0, terminalText1, terminalText2, terminalText3, terminalText4, terminalText5;

    List<String> terminalList = Arrays.asList(terminalText0, terminalText1, terminalText2, terminalText3, terminalText4, terminalText5);

    float width;
    float height;
    int elementWidth;
    int elementHeight;
    int pt;

    public GameSurface(Context context) {
        super(context);
        this.setFocusable(true);
        this.getHolder().addCallback(this);

        gameThread = new GameThread(getHolder(), this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        this.gameThread.setRunning(true);
        this.gameThread.start();

        width = this.getWidth();
        height = this.getHeight();
        elementHeight = (int) height / screenMatrixCurrent.length;
        elementWidth = (int) width / screenMatrixCurrent[0].length;

        pt = elementHeight / 10;

        mainCharacter = new MainCharacter(BitmapFactory.decodeResource(this.getResources(), R.drawable.chibi1), (int) width / 2, (int) height / 2);
        mainCharacter.setText("This is you!");
        mainCharacter.setVector(0, 1);

        this.updateBackground();
    }

    public void update() {

        if (mainCharacter.getX() > width - mainCharacter.getWidth() / 2) {
            if (screenMatrixCurrent == ScreenMatrix.screenMatrix0) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix1;
                this.updateBackground();
                mainCharacter.setX(-mainCharacter.getWidth() / 2 + 1);
            } else if (screenMatrixCurrent == ScreenMatrix.screenMatrix1) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix2;
                this.updateBackground();
                mainCharacter.setX(-mainCharacter.getWidth() / 2 + 1);
            } else if (screenMatrixCurrent == ScreenMatrix.screenMatrix3) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix4;
                this.updateBackground();
                mainCharacter.setX(-mainCharacter.getWidth() / 2 + 1);
            } else if (screenMatrixCurrent == ScreenMatrix.screenMatrix4) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix5;
                this.updateBackground();
                mainCharacter.setX(-mainCharacter.getWidth() / 2 + 1);
            } else if (screenMatrixCurrent == ScreenMatrix.screenMatrix6) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix7;
                this.updateBackground();
                mainCharacter.setX(-mainCharacter.getWidth() / 2 + 1);
            } else if (screenMatrixCurrent == ScreenMatrix.screenMatrix7) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix8;
                this.updateBackground();
                mainCharacter.setX(-mainCharacter.getWidth() / 2 + 1);
            }
        }
        if (mainCharacter.getX() < -mainCharacter.getWidth() / 2) {
            if (screenMatrixCurrent == ScreenMatrix.screenMatrix1) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix0;
                this.updateBackground();
                mainCharacter.setX(this.getWidth() - mainCharacter.getWidth() / 2 - 1);
            } else if (screenMatrixCurrent == ScreenMatrix.screenMatrix2) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix1;
                this.updateBackground();
                mainCharacter.setX(this.getWidth() - mainCharacter.getWidth() / 2 - 1);
            } else if (screenMatrixCurrent == ScreenMatrix.screenMatrix4) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix3;
                this.updateBackground();
                mainCharacter.setX(this.getWidth() - mainCharacter.getWidth() / 2 - 1);
            } else if (screenMatrixCurrent == ScreenMatrix.screenMatrix5) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix4;
                this.updateBackground();
                mainCharacter.setX(this.getWidth() - mainCharacter.getWidth() / 2 - 1);
            } else if (screenMatrixCurrent == ScreenMatrix.screenMatrix7) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix6;
                this.updateBackground();
                mainCharacter.setX(this.getWidth() - mainCharacter.getWidth() / 2 - 1);
            } else if (screenMatrixCurrent == ScreenMatrix.screenMatrix8) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix7;
                this.updateBackground();
                mainCharacter.setX(this.getWidth() - mainCharacter.getWidth() / 2 - 1);
            }
        }
        if (mainCharacter.getY() < -mainCharacter.getHeight() / 2) {
            if (screenMatrixCurrent == ScreenMatrix.screenMatrix3) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix0;
                this.updateBackground();
                mainCharacter.setY(this.getHeight() - 3 * elementHeight - mainCharacter.getHeight() / 2 - 1);
            } else if (screenMatrixCurrent == ScreenMatrix.screenMatrix4) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix1;
                this.updateBackground();
                mainCharacter.setY(this.getHeight() - 3 * elementHeight - mainCharacter.getHeight() / 2 - 1);
            } else if (screenMatrixCurrent == ScreenMatrix.screenMatrix5) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix2;
                this.updateBackground();
                mainCharacter.setY(this.getHeight() - 3 * elementHeight - mainCharacter.getHeight() / 2 - 1);
            } else if (screenMatrixCurrent == ScreenMatrix.screenMatrix6) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix3;
                this.updateBackground();
                mainCharacter.setY(this.getHeight() - 3 * elementHeight - mainCharacter.getHeight() / 2 - 1);
            } else if (screenMatrixCurrent == ScreenMatrix.screenMatrix7) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix4;
                this.updateBackground();
                mainCharacter.setY(this.getHeight() - 3 * elementHeight - mainCharacter.getHeight() / 2 - 1);
            } else if (screenMatrixCurrent == ScreenMatrix.screenMatrix8) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix5;
                this.updateBackground();
                mainCharacter.setY(this.getHeight() - 3 * elementHeight - mainCharacter.getHeight() / 2 - 1);
            }
        }
        if (mainCharacter.getY() > height - 3 * elementHeight - mainCharacter.getHeight() / 2) {
            if (screenMatrixCurrent == ScreenMatrix.screenMatrix0) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix3;
                this.updateBackground();
                mainCharacter.setY(-mainCharacter.getHeight() / 2 + 1);
            } else if (screenMatrixCurrent == ScreenMatrix.screenMatrix1) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix4;
                this.updateBackground();
                mainCharacter.setY(-mainCharacter.getHeight() / 2 + 1);
            } else if (screenMatrixCurrent == ScreenMatrix.screenMatrix2) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix5;
                this.updateBackground();
                mainCharacter.setY(-mainCharacter.getHeight() / 2 + 1);
            } else if (screenMatrixCurrent == ScreenMatrix.screenMatrix3) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix6;
                this.updateBackground();
                mainCharacter.setY(-mainCharacter.getHeight() / 2 + 1);
            } else if (screenMatrixCurrent == ScreenMatrix.screenMatrix4) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix7;
                this.updateBackground();
                mainCharacter.setY(-mainCharacter.getHeight() / 2 + 1);
            } else if (screenMatrixCurrent == ScreenMatrix.screenMatrix5) {
                screenMatrixCurrent = ScreenMatrix.screenMatrix8;
                this.updateBackground();
                mainCharacter.setY(-mainCharacter.getHeight() / 2 + 1);
            }
        }

        for (int i = 0; i < unwalkableTerrainList.size(); i++) {
            switch (obstacle(mainCharacter, unwalkableTerrainList.get(i))) {
                case 1: //obstacle on top
                    mainCharacter.setMoving(false);
                    mainCharacter.setY(unwalkableTerrainList.get(i).getY() + unwalkableTerrainList.get(i).getHeight() + 1);
                    break;
                case 2: //obstacle on left
                    mainCharacter.setMoving(false);
                    mainCharacter.setX(unwalkableTerrainList.get(i).getX() + unwalkableTerrainList.get(i).getWidth() + 1);
                    break;
                case 3: //obstacle on right
                    mainCharacter.setMoving(false);
                    mainCharacter.setX(unwalkableTerrainList.get(i).getX() - mainCharacter.getWidth() - 1);
                    break;
                case 4: //obstacle on bottom
                    mainCharacter.setMoving(false);
                    mainCharacter.setY(unwalkableTerrainList.get(i).getY() - mainCharacter.getHeight() - 1);
                    break;
            }
        }
        if (npcList.size() > 0) {
            for (int i = 0; i < unwalkableTerrainList.size(); i++) {
                for (int j = 0; j < npcList.size(); j++) {
                    switch (obstacle(npcList.get(j), unwalkableTerrainList.get(i))) {
                        case 1: //obstacle on top
                            npcList.get(j).setMoving(false);
                            npcList.get(j).setY(unwalkableTerrainList.get(i).getY() + unwalkableTerrainList.get(i).getHeight() + 1);
                            npcList.get(j).setAiWait(true);
                            break;
                        case 2: //obstacle on left
                            npcList.get(j).setMoving(false);
                            npcList.get(j).setX(unwalkableTerrainList.get(i).getX() + unwalkableTerrainList.get(i).getWidth() + 1);
                            npcList.get(j).setAiWait(true);
                            break;
                        case 3: //obstacle on right
                            npcList.get(j).setMoving(false);
                            npcList.get(j).setX(unwalkableTerrainList.get(i).getX() - mainCharacter.getWidth() - 1);
                            npcList.get(j).setAiWait(true);
                            break;
                        case 4: //obstacle on bottom
                            npcList.get(j).setMoving(false);
                            npcList.get(j).setY(unwalkableTerrainList.get(i).getY() - mainCharacter.getHeight() - 1);
                            npcList.get(j).setAiWait(true);
                            break;
                    }
                }
            }
            for (int i = 0; i < npcList.size(); i++) {
                switch (collision(mainCharacter, npcList.get(i))) {
                    case 1: //obstacle on top
                        mainCharacter.setMoving(false);
                        mainCharacter.setY(npcList.get(i).getY() + npcList.get(i).getHeight() + 1);
                        break;
                    case 2: //obstacle on left
                        mainCharacter.setMoving(false);
                        mainCharacter.setX(npcList.get(i).getX() + npcList.get(i).getWidth() + 1);
                        break;
                    case 3: //obstacle on right
                        mainCharacter.setMoving(false);
                        mainCharacter.setX(npcList.get(i).getX() - mainCharacter.getWidth() - 1);
                        break;
                    case 4: //obstacle on bottom
                        mainCharacter.setMoving(false);
                        mainCharacter.setY(npcList.get(i).getY() - mainCharacter.getHeight() - 1);
                        break;
                }
            }
        }

        for (NPCharacter npc : npcList) {
            npc.update();
        }

        mainCharacter.update();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            int x = (int) event.getX();
            int y = (int) event.getY();

            // MOVEMENT CONTROLS

            if (b_left.getRectangle().contains(x, y) && !popupActive) {
                mainCharacter.setVector(-1, 0);
                mainCharacter.setMoving(true);

            } else if (b_up.getRectangle().contains(x, y) && !popupActive) {
                mainCharacter.setVector(0, -1);
                mainCharacter.setMoving(true);

            } else if (b_right.getRectangle().contains(x, y) && !popupActive) {
                mainCharacter.setVector(1, 0);
                mainCharacter.setMoving(true);

            } else if (b_down.getRectangle().contains(x, y) && !popupActive) {
                mainCharacter.setVector(0, 1);
                mainCharacter.setMoving(true);
            }

            // OBJECT CLICK

            Iterator<TerrainElement> iterator = this.unwalkableTerrainList.iterator();
            while (iterator.hasNext() && !popupActive) {
                TerrainElement element = iterator.next();
                if (element.getX() < x && x < element.getX() + element.getWidth()
                        && element.getY() < y && y < element.getY() + element.getHeight()) {

                    terminalText = element.getText();
                    b_talkActive = false;

                }
            }

            Iterator<NPCharacter> iterator0 = this.npcList.iterator();
            while (iterator0.hasNext() && !popupActive) {
                NPCharacter npc = iterator0.next();
                if (npc.getX() < x && x < npc.getX() + npc.getWidth()
                        && npc.getY() < y && y < npc.getY() + npc.getHeight()) {

                    terminalText = npc.getText();

                    switch (terminalText) {

                        case "Russian Gopnik":

                            popupList.clear();
                            talkMatrixCurrent = TalkMatrices.talkMatrix0;
                            currentDialogueMatrix = TalkMatrices.dialogueMatrix0;
                            dialogueMatrix = currentDialogueMatrix[0];
                            faceBitmap = R.drawable.gopnik_face;
                            if (distanceBetween(mainCharacter, npc) < 20 * pt) {
                                b_talkActive = true;
                            }
                            break;

                        case "Batman":

                            popupList.clear();
                            talkMatrixCurrent = TalkMatrices.talkMatrix1;
                            currentDialogueMatrix = TalkMatrices.dialogueMatrix1;
                            dialogueMatrix = currentDialogueMatrix[0];
                            faceBitmap = R.drawable.batman_face;
                            if (distanceBetween(mainCharacter, npc) < 20 * pt) {
                                b_talkActive = true;
                            }
                            break;
                    }
                }
            }

            if (mainCharacter.getX() < x && x < mainCharacter.getX() + mainCharacter.getWidth()
                    && mainCharacter.getY() < y && y < mainCharacter.getY() + mainCharacter.getHeight() && !popupActive) {

                terminalText = mainCharacter.getText();
                b_talkActive = false;
            }

            if (b_talk.getRectangle().contains(x, y) && b_talkActive && !popupActive) {

                popupActive = true;
                b_talkActive = false;

                for (int i = 0; i < terminalList.size(); i++) {
                    terminalList.set(i, talkMatrixCurrent[0][i]);
                }
                popup();
            }

            for (PopupElement element : popupList) {
                if (element.getX() < x && x < element.getX() + element.getWidth() && element.getY() < y && y < element.getY() + element.getHeight()) {
                    switch (element.getText()) {
                        case "close":
                            popupActive = false;
                            break;
                        case "choice 1":
                            switch (dialogueMatrix[0]) {
                                case 1:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[1][i]);
                                        dialogueMatrix = currentDialogueMatrix[1];
                                    }
                                    break;
                                case 2:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[2][i]);
                                        dialogueMatrix = currentDialogueMatrix[2];
                                    }
                                    break;
                                case 3:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[3][i]);
                                        dialogueMatrix = currentDialogueMatrix[3];
                                    }
                                    break;
                                case 4:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[4][i]);
                                        dialogueMatrix = currentDialogueMatrix[4];
                                    }
                                    break;
                                case 5:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[5][i]);
                                        dialogueMatrix = currentDialogueMatrix[5];
                                    }
                                    break;
                                case 6:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[6][i]);
                                        dialogueMatrix = currentDialogueMatrix[6];
                                    }
                                    break;
                                case 0:
                                    popupActive = false;
                                    break;
                            }
                            break;
                        case "choice 2":
                            switch (dialogueMatrix[1]) {
                                case 1:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[1][i]);
                                        dialogueMatrix = currentDialogueMatrix[1];
                                    }
                                    break;
                                case 2:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[2][i]);
                                        dialogueMatrix = currentDialogueMatrix[2];
                                    }
                                    break;
                                case 3:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[3][i]);
                                        dialogueMatrix = currentDialogueMatrix[3];
                                    }
                                    break;
                                case 4:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[4][i]);
                                        dialogueMatrix = currentDialogueMatrix[4];
                                    }
                                    break;
                                case 5:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[5][i]);
                                        dialogueMatrix = currentDialogueMatrix[5];
                                    }
                                    break;
                                case 6:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[6][i]);
                                        dialogueMatrix = currentDialogueMatrix[6];
                                    }
                                    break;
                                case 0:
                                    popupActive = false;
                                    break;
                            }
                            break;
                        case "choice 3":
                            switch (dialogueMatrix[2]) {
                                case 1:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[1][i]);
                                        dialogueMatrix = currentDialogueMatrix[1];
                                    }
                                    break;
                                case 2:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[2][i]);
                                        dialogueMatrix = currentDialogueMatrix[2];
                                    }
                                    break;
                                case 3:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[3][i]);
                                        dialogueMatrix = currentDialogueMatrix[3];
                                    }
                                    break;
                                case 4:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[4][i]);
                                        dialogueMatrix = currentDialogueMatrix[4];
                                    }
                                    break;
                                case 5:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[5][i]);
                                        dialogueMatrix = currentDialogueMatrix[5];
                                    }
                                    break;
                                case 6:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[6][i]);
                                        dialogueMatrix = currentDialogueMatrix[6];
                                    }
                                    break;
                                case 0:
                                    popupActive = false;
                                    break;
                            }
                            break;
                        case "choice 4":
                            switch (dialogueMatrix[3]) {
                                case 1:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[1][i]);
                                        dialogueMatrix = currentDialogueMatrix[1];
                                    }
                                    break;
                                case 2:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[2][i]);
                                        dialogueMatrix = currentDialogueMatrix[2];
                                    }
                                    break;
                                case 3:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[3][i]);
                                        dialogueMatrix = currentDialogueMatrix[3];
                                    }
                                    break;
                                case 4:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[4][i]);
                                        dialogueMatrix = currentDialogueMatrix[4];
                                    }
                                    break;
                                case 5:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[5][i]);
                                        dialogueMatrix = currentDialogueMatrix[5];
                                    }
                                    break;
                                case 6:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[6][i]);
                                        dialogueMatrix = currentDialogueMatrix[6];
                                    }
                                    break;
                                case 0:
                                    popupActive = false;
                                    break;
                            }
                            break;
                        case "choice 5":
                            switch (dialogueMatrix[4]) {
                                case 1:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[1][i]);
                                        dialogueMatrix = currentDialogueMatrix[1];
                                    }
                                    break;
                                case 2:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[2][i]);
                                        dialogueMatrix = currentDialogueMatrix[2];
                                    }
                                    break;
                                case 3:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[3][i]);
                                        dialogueMatrix = currentDialogueMatrix[3];
                                    }
                                    break;
                                case 4:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[4][i]);
                                        dialogueMatrix = currentDialogueMatrix[4];
                                    }
                                    break;
                                case 5:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[5][i]);
                                        dialogueMatrix = currentDialogueMatrix[5];
                                    }
                                    break;
                                case 6:
                                    for (int i = 0; i < terminalList.size(); i++) {
                                        terminalList.set(i, talkMatrixCurrent[6][i]);
                                        dialogueMatrix = currentDialogueMatrix[6];
                                    }
                                    break;
                                case 0:
                                    popupActive = false;
                                    break;
                            }
                            break;
                    }
                }
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mainCharacter.setMoving(false);
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);

        final int savedState = canvas.save();

        for (TerrainElement element : walkableTerrainList) {
            element.draw(canvas);
        }

        for (TerrainElement element : unwalkableTerrainList) {
            element.draw(canvas);
        }

        mainCharacter.draw(canvas);

        for (NPCharacter npc : npcList) {
            npc.draw(canvas);
        }

        for (InterfaceElement element : interfaceList) {
            element.draw(canvas);
        }

        terminal.draw(canvas);

        b_left.draw(canvas);
        b_up.draw(canvas);
        b_right.draw(canvas);
        b_down.draw(canvas);

        if (b_talkActive) {
            b_talk.draw(canvas);
        }

        if (popupActive) {
            for (PopupElement element : popupList) {
                element.draw(canvas);
            }
            TextPaint paint = new TextPaint();
            paint.setColor(Color.GREEN);
            paint.setTextSize((popupList.get(2).getWidth() / 20));

            StaticLayout mTextLayout0 = new StaticLayout(terminalList.get(0), paint, (int) (popupList.get(2).getWidth() * 0.95), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            canvas.save();
            canvas.translate(popupList.get(2).getX() + (int) (popupList.get(2).getWidth() * 0.03),
                    popupList.get(2).getY() + (int) (popupList.get(2).getHeight() * 0.05));
            mTextLayout0.draw(canvas);
            canvas.restore();

            StaticLayout mTextLayout1 = new StaticLayout(terminalList.get(1), paint, (int) (popupList.get(3).getWidth() * 0.95), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            canvas.save();
            canvas.translate(popupList.get(3).getX() + (int) (popupList.get(3).getWidth() * 0.03),
                    popupList.get(3).getY() + (int) (popupList.get(3).getHeight() * 0.05));
            mTextLayout1.draw(canvas);
            canvas.restore();

            StaticLayout mTextLayout2 = new StaticLayout(terminalList.get(2), paint, (int) (popupList.get(4).getWidth() * 0.94), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            canvas.save();
            canvas.translate(popupList.get(4).getX() + (int) (popupList.get(4).getWidth() * 0.03),
                    popupList.get(4).getY() + (int) (popupList.get(4).getHeight() * 0.04));
            mTextLayout2.draw(canvas);
            canvas.restore();

            StaticLayout mTextLayout3 = new StaticLayout(terminalList.get(3), paint, (int) (popupList.get(5).getWidth() * 0.95), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            canvas.save();
            canvas.translate(popupList.get(5).getX() + (int) (popupList.get(5).getWidth() * 0.03),
                    popupList.get(5).getY() + (int) (popupList.get(5).getHeight() * 0.05));
            mTextLayout3.draw(canvas);
            canvas.restore();

            StaticLayout mTextLayout4 = new StaticLayout(terminalList.get(4), paint, (int) (popupList.get(6).getWidth() * 0.95), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            canvas.save();
            canvas.translate(popupList.get(6).getX() + (int) (popupList.get(6).getWidth() * 0.03),
                    popupList.get(6).getY() + (int) (popupList.get(6).getHeight() * 0.05));
            mTextLayout4.draw(canvas);
            canvas.restore();

            StaticLayout mTextLayout5 = new StaticLayout(terminalList.get(5), paint, (int) (popupList.get(7).getWidth() * 0.95), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            //canvas.save();
            canvas.translate(popupList.get(7).getX() + (int) (popupList.get(7).getWidth() * 0.03),
                    popupList.get(7).getY() + (int) (popupList.get(7).getHeight() * 0.05));
            mTextLayout5.draw(canvas);
            canvas.restore();
        }

        TextPaint paint = new TextPaint();
        paint.setColor(Color.GREEN);
        paint.setTextSize((int) (terminal.getWidth() / 15));

        StaticLayout mTextLayout = new StaticLayout(terminalText, paint, (int) (terminal.getWidth() * 0.95), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        //canvas.save();
        canvas.translate(terminal.getX() + (int) (terminal.getWidth() * 0.03),
                terminal.getY() + (int) (terminal.getHeight() * 0.05));
        mTextLayout.draw(canvas);

        canvas.restoreToCount(savedState);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                this.gameThread.setRunning(false);
                this.gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public int collision(SpriteObject a, SpriteObject b) {
        //obstacle on top
        if (b.getRectangle().contains(a.getX() + (a.getWidth() / 2), a.getY())) {
            return 1;
        }
        //obstacle on left
        if (b.getRectangle().contains(a.getX(), a.getY() + (a.getHeight() / 2))) {
            return 2;
        }
        //obstacle on right
        if (b.getRectangle().contains(a.getX() + a.getWidth(), a.getY() + (a.getHeight() / 2))) {
            return 3;
        }
        //obstacle on bottom
        if (b.getRectangle().contains(a.getX() + (a.getWidth() / 2), a.getY() + a.getHeight())) {
            return 4;
        }
        return 0;
    }

    public int distanceBetween(SpriteObject a, SpriteObject b) {

        return (int) Math.sqrt(Math.pow((a.getX() + a.getWidth() / 2) - (b.getX() + b.getWidth() / 2), 2) +
                Math.pow((a.getY() + a.getHeight() / 2) - (b.getY() + b.getHeight() / 2), 2));

    }

    public int obstacle(SpriteObject a, TerrainElement b) {
        //obstacle on top
        if (b.getRectangle().contains(a.getX() + (a.getWidth() / 2), a.getY())) {
            return 1;
        }
        //obstacle on left
        if (b.getRectangle().contains(a.getX(), a.getY() + (a.getHeight() / 2))) {
            return 2;
        }
        //obstacle on right
        if (b.getRectangle().contains(a.getX() + a.getWidth(), a.getY() + (a.getHeight() / 2))) {
            return 3;
        }
        //obstacle on bottom
        if (b.getRectangle().contains(a.getX() + (a.getWidth() / 2), a.getY() + a.getHeight())) {
            return 4;
        }
        return 0;
    }

    public void updateBackground() {

        walkableTerrainList.clear();
        unwalkableTerrainList.clear();
        popupList.clear();
        npcList.clear();

        for (int j = 0; j < screenMatrixCurrent.length; j++) {
            for (int i = 0; i < screenMatrixCurrent[0].length; i++) {
                switch (screenMatrixCurrent[j][i]) {
                    case 0:
                        TerrainElement stone = new TerrainElement(
                                new Rect(i * elementWidth, j * elementHeight, (i + 1) * elementWidth, (j + 1) * elementHeight),
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.stone));
                        stone.setText("Structure made of stone");
                        this.unwalkableTerrainList.add(stone);
                        break;
                    case 1:
                        TerrainElement grass = new TerrainElement(
                                new Rect(i * elementWidth, j * elementHeight, (i + 1) * elementWidth, (j + 1) * elementHeight),
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.grass));
                        this.walkableTerrainList.add(grass);
                        break;
                    case 2:
                        TerrainElement tree1 = new TerrainElement(
                                new Rect(i * elementWidth, j * elementHeight, (i + 1) * elementWidth, (j + 1) * elementHeight),
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.tree1));
                        tree1.setText("Oak tree");
                        this.unwalkableTerrainList.add(tree1);
                        break;
                    case 3:
                        TerrainElement tree2 = new TerrainElement(
                                new Rect(i * elementWidth, j * elementHeight, (i + 1) * elementWidth, (j + 1) * elementHeight),
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.tree2));
                        tree2.setText("Mahogany tree");
                        this.unwalkableTerrainList.add(tree2);
                        break;
                    case 99:
                        InterfaceElement hudBlock = new InterfaceElement(
                                new Rect(i * elementWidth, j * elementHeight, (i + 1) * elementWidth, (j + 1) * elementHeight),
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.dashboard));
                        this.interfaceList.add(hudBlock);
                        break;
                    case 101:
                        TerrainElement grass1 = new TerrainElement(
                                new Rect(i * elementWidth, j * elementHeight, (i + 1) * elementWidth, (j + 1) * elementHeight),
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.grass));
                        NPCharacter npc = new NPCharacter(BitmapFactory.decodeResource(this.getResources(), R.drawable.chibi2), i * elementWidth, j * elementHeight);
                        npc.setText("Russian Gopnik");
                        npc.setVector(0, 1);
                        npc.setAi(1);
                        this.walkableTerrainList.add(grass1);
                        this.npcList.add(npc);
                        break;
                    case 102:
                        TerrainElement grass2 = new TerrainElement(
                                new Rect(i * elementWidth, j * elementHeight, (i + 1) * elementWidth, (j + 1) * elementHeight),
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.grass));
                        NPCharacter batman = new NPCharacter(BitmapFactory.decodeResource(this.getResources(), R.drawable.batman), i * elementWidth, j * elementHeight);
                        batman.setText("Batman");
                        batman.setVector(0, 1);
                        batman.setAi(2);
                        this.walkableTerrainList.add(grass2);
                        this.npcList.add(batman);
                        break;
                    case 103:
                        TerrainElement grass3 = new TerrainElement(
                                new Rect(i * elementWidth, j * elementHeight, (i + 1) * elementWidth, (j + 1) * elementHeight),
                                BitmapFactory.decodeResource(this.getResources(), R.drawable.grass));
                        NPCharacter cat = new NPCharacter(BitmapFactory.decodeResource(this.getResources(), R.drawable.cat), i * elementWidth, j * elementHeight);
                        cat.setText("Stray cat");
                        cat.setVector(0, 1);
                        cat.setAi(3);
                        this.walkableTerrainList.add(grass3);
                        this.npcList.add(cat);
                        break;
                }
            }
        }
        b_left = new Button(new Rect(3 * pt, 140 * pt, 13 * pt, 150 * pt),
                BitmapFactory.decodeResource(this.getResources(), R.drawable.buttonleft));
        b_up = new Button(new Rect(13 * pt, 130 * pt, 23 * pt, 140 * pt),
                BitmapFactory.decodeResource(this.getResources(), R.drawable.buttonup));
        b_right = new Button(new Rect(23 * pt, 140 * pt, 33 * pt, 150 * pt),
                BitmapFactory.decodeResource(this.getResources(), R.drawable.buttonright));
        b_down = new Button(new Rect(13 * pt, 150 * pt, 23 * pt, 160 * pt),
                BitmapFactory.decodeResource(this.getResources(), R.drawable.buttondown));

        b_talk = new Button(new Rect(36 * pt, 151 * pt, 44 * pt, 159 * pt),
                BitmapFactory.decodeResource(this.getResources(), R.drawable.buttontalk));

        terminal = new InterfaceElement(
                new Rect(36 * pt, 133 * pt, 87 * pt, 149 * pt),
                BitmapFactory.decodeResource(this.getResources(), R.drawable.terminal));
    }

    public void popup() {

        PopupElement metal_bg_top = new PopupElement(new Rect(3 * pt, 3 * pt, 87 * pt, 43 * pt),
                BitmapFactory.decodeResource(this.getResources(), R.drawable.metal_bg_top));
        PopupElement metal_bg_bot = new PopupElement(new Rect(3 * pt, 43 * pt, 87 * pt, 127 * pt),
                BitmapFactory.decodeResource(this.getResources(), R.drawable.metal_bg_bot));

        PopupElement terminal1 = new PopupElement(new Rect(6 * pt, 46 * pt, 84 * pt, 71 * pt),
                BitmapFactory.decodeResource(this.getResources(), R.drawable.terminal));
        PopupElement terminal2line1 = new PopupElement(new Rect(6 * pt, 74 * pt, 84 * pt, 84 * pt),
                BitmapFactory.decodeResource(this.getResources(), R.drawable.terminal));
        terminal2line1.setText("choice 1");
        PopupElement terminal2line2 = new PopupElement(new Rect(6 * pt, 84 * pt, 84 * pt, 94 * pt),
                BitmapFactory.decodeResource(this.getResources(), R.drawable.terminal));
        terminal2line2.setText("choice 2");
        PopupElement terminal2line3 = new PopupElement(new Rect(6 * pt, 94 * pt, 84 * pt, 104 * pt),
                BitmapFactory.decodeResource(this.getResources(), R.drawable.terminal));
        terminal2line3.setText("choice 3");
        PopupElement terminal2line4 = new PopupElement(new Rect(6 * pt, 104 * pt, 84 * pt, 114 * pt),
                BitmapFactory.decodeResource(this.getResources(), R.drawable.terminal));
        terminal2line4.setText("choice 4");
        PopupElement terminal2line5 = new PopupElement(new Rect(6 * pt, 114 * pt, 84 * pt, 124 * pt),
                BitmapFactory.decodeResource(this.getResources(), R.drawable.terminal));
        terminal2line5.setText("choice 5");

        PopupElement face = new PopupElement(new Rect(3 * pt, 3 * pt, 53 * pt, 43 * pt),
                BitmapFactory.decodeResource(this.getResources(), faceBitmap));
        PopupElement metal_bg_illuminator = new PopupElement(new Rect(3 * pt, 3 * pt, 53 * pt, 43 * pt),
                BitmapFactory.decodeResource(this.getResources(), R.drawable.metal_bg_illuminator));

        popupList.add(metal_bg_top);
        popupList.add(metal_bg_bot);
        popupList.add(terminal1);
        popupList.add(terminal2line1);
        popupList.add(terminal2line2);
        popupList.add(terminal2line3);
        popupList.add(terminal2line4);
        popupList.add(terminal2line5);
        popupList.add(face);
        popupList.add(metal_bg_illuminator);
    }
}





