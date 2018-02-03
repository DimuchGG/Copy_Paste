import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import javax.swing.text.MyEditorKit;

public class HintTextField extends JTextField implements FocusListener {
    private static final String CUT = "Вырезать";
    private static final String COPY = "Копировать";
    private static final String PASTE = "Вставить";
    private static final String SELECT_ALL = "Выделить все";
    private final String hint;
    private boolean showingHint;

    public HintTextField(String hint) {
        super(hint);
        this.hint = hint;
        this.showingHint = true;
        super.addFocusListener(this);

        tuningActions();
    }

    public HintTextField(String hint, int width, int height) {
        super(hint);
        this.hint = hint;
        this.showingHint = true;
        super.addFocusListener(this);

        this.setPreferredSize(new Dimension(width, height));
        tuningActions();
    }

    private void tuningActions() {
        JPopupMenu menu = new JPopupMenu();
        Action cut = new MyEditorKit.CutAction();
        cut.putValue(Action.NAME, CUT);
        cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
        menu.add( cut );

        Action copy = new MyEditorKit.CopyAction();
        copy.putValue(Action.NAME, COPY);
        copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
        menu.add( copy );

        Action paste = new MyEditorKit.PasteAction();
        paste.putValue(Action.NAME, PASTE);
        paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
        menu.add( paste );

        Action selectAll = new MyEditorKit.SelectAllAction();
        selectAll.putValue(Action.NAME, SELECT_ALL);
        selectAll.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
        menu.add( selectAll );

        this.setComponentPopupMenu( menu );
    }

    public void setShowingHint(boolean showingHint) {
        this.showingHint = showingHint;
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (getText().isEmpty()) {
            super.setText("");
            showingHint = false;
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (getText().isEmpty()) {
            super.setText(hint);
            showingHint = true;
        }
    }

    @Override
    public String getText() {
        return showingHint ? "" : super.getText();
    }

    @Override
    public void setText(String t) {
        showingHint = false;
        super.setText(t);
    }
}