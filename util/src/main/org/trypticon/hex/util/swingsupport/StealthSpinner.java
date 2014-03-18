package org.trypticon.hex.util.swingsupport;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 * A spinner which looks a lot like a label when it isn't being used to edit the value.
 */
public class StealthSpinner extends JSpinner {
    public StealthSpinner(SpinnerModel model) {
        super(model);

        setBorder(BorderFactory.createEmptyBorder());
        setOpaque(false);
    }

    @Override
    protected JComponent createEditor(SpinnerModel model) {
        if (model instanceof SpinnerNumberModel) {
            return new StealthNumberEditor(this);
        } else {
            return super.createEditor(model);
        }
    }

    /**
     * Stealth version of the editor for numbers.
     */
    public static class StealthNumberEditor extends NumberEditor {
        public StealthNumberEditor(JSpinner spinner) {
            super(spinner);

            setBorder(BorderFactory.createEmptyBorder());
            setOpaque(false);

            remove(getTextField());

            JFormattedTextField ftf = new StealthFormattedTextField();
            ftf.setName("Spinner.formattedTextField");
            ftf.setValue(spinner.getValue());
            ftf.addPropertyChangeListener(this);
            ftf.setEditable(false);
            ftf.setInheritsPopupMenu(true);

            String toolTipText = spinner.getToolTipText();
            if (toolTipText != null) {
                ftf.setToolTipText(toolTipText);
            }

            add(ftf);
        }
    }
}
