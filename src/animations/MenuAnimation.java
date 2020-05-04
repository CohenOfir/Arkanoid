package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Animation;
import interfaces.Menu;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Menu Animation Class.
 * Author - Ofir Cohen.
 * @param <T> - multiple return types.
 */
public class MenuAnimation<T> implements Menu<T>, Animation {

    private KeyboardSensor keyboardSensor;
    private AnimationRunner animationRunner;
    private boolean shouldStop;
    private List<String> keysList;
    private List<String> messagesList;
    private List<T> returnVals;
    private T status;
    private String menuTitle;
    private List<Menu<T>> subMenuList;
    private List<Boolean> isSubMenuList;


    /**
     * @param ks        - Keyboard sensor.
     * @param ar        - Animation runner.
     * @param menuTitle - menu title name.
     */
    public MenuAnimation(KeyboardSensor ks, AnimationRunner ar, String menuTitle) {
        this.keyboardSensor = ks;
        this.animationRunner = ar;
        this.shouldStop = false;
        this.keysList = new ArrayList<String>();
        this.messagesList = new ArrayList<String>();
        this.returnVals = new ArrayList<T>();
        this.status = null;
        this.menuTitle = menuTitle;
        this.subMenuList = new ArrayList<Menu<T>>();
        this.isSubMenuList = new ArrayList<Boolean>();
    }


    /**
     * add a new selection to the main menu.
     *
     * @param key       - key trigger animation.
     * @param message   - Describes Animation.
     * @param returnVal T - creating and running the specific interfaces.Animation
     */
    public void addSelection(String key, String message, T returnVal) {
        keysList.add(key);
        messagesList.add(message);
        returnVals.add(returnVal);
        this.subMenuList.add(null);
        this.isSubMenuList.add(false);

    }

    /**
     * @return status (play a game, show high score, quit).
     */
    public T getStatus() {
        T currentStatus = this.status;
        this.status = null;
        return currentStatus;
    }

    /**
     * @param key     - key trigger animation.
     * @param message - Describes Animation.
     * @param subMenu - creating and displaying the specific sub menu
     */
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.keysList.add(key);
        this.messagesList.add(message);
        this.returnVals.add(null);
        this.subMenuList.add(subMenu);
        this.isSubMenuList.add(true);
    }

    /**
     * @param d - DrawSurface given from AnimationRunner.
     */
    public void doOneFrame(DrawSurface d) {
        //drawings
        int keyWidth = d.getWidth() / 10;
        int keyHeight = d.getHeight() / 4;
        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.yellow);
        d.drawText(d.getWidth() / 10, d.getHeight() / 10, this.menuTitle, 50);
        d.setColor(Color.GREEN);
        for (int i = 0; i < keysList.size(); i++) {
            d.drawText(keyWidth, keyHeight, "(" + keysList.get(i) + ") - " + messagesList.get(i), 30);
            keyHeight += 50;
        }

        //logic
        for (int i = 0; i < keysList.size(); i++) {
            if (keyboardSensor.isPressed(keysList.get(i)) && (this.isSubMenuList.get(i))) {
                this.animationRunner.run(this.subMenuList.get(i));
                this.shouldStop = true;
                this.status = this.subMenuList.get(i).getStatus();
                break;

            } else if (keyboardSensor.isPressed(keysList.get(i)) && !(this.isSubMenuList.get(i))) {
                this.shouldStop = true;
                this.status = this.returnVals.get(i);

            }
        }

    }

    /**
     * @return Boolean value - determines when to stop the animation.
     */
    public boolean shouldStop() {
        return this.shouldStop;
    }

    /**
     * @param flag - true if animation should stop.
     */
    public void setShouldStop(boolean flag) {
        this.shouldStop = flag;
    }

}
