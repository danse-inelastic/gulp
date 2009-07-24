package javagulp.view.potential;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class IconHeaderRenderer extends DefaultTableCellRenderer implements Serializable {
	private static final long serialVersionUID = 1749847402689539968L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// Inherit the colors and font from the header component
		if (table != null) {
			final JTableHeader header = table.getTableHeader();
			if (header != null) {
				setForeground(header.getForeground());
				setBackground(header.getBackground());
				setFont(header.getFont());
			}
		}
		if (value instanceof Icon) {
			// Value is an Icon
			setIcon((Icon) value);
			setText("");
		} else {
			// Value is text
			setText((value == null) ? "" : value.toString());
			setIcon(null);
		}
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		setHorizontalAlignment(SwingConstants.CENTER);
		return this;
	}
}