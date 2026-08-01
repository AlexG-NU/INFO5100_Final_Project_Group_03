package UserInterface.Compliance;

import java.awt.Component;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * Shared readability settings for the Compliance work-area tables.
 *
 * @author janet
 */
final class ComplianceTableUI {

    private ComplianceTableUI() {
    }

    static void configure(JTable table, int... widths) {
        // Keep every field visible in the table at the same time.  The
        // preferred widths below still control the relative amount of space
        // each column receives, but JTable scales them to the viewport instead
        // of requiring the user to scroll sideways.
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setRowHeight(26);
        // Leave the unused part of the viewport blank instead of drawing
        // dozens of empty grid rows below a short result set.
        table.setFillsViewportHeight(false);
        table.setGridColor(new Color(210, 210, 190));
        table.setSelectionBackground(new Color(204, 229, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(
                table.getTableHeader().getFont().deriveFont(Font.BOLD));
        table.getTableHeader().setBackground(new Color(238, 238, 210));
        table.setDefaultRenderer(Object.class, new TooltipRenderer());
        for (int column = 0;
                column < widths.length && column < table.getColumnCount();
                column++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            tableColumn.setPreferredWidth(widths[column]);
            tableColumn.setMinWidth(24);
        }
    }

    static void styleScrollPane(JScrollPane scrollPane, int height) {
        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(760, height));
        scrollPane.setMinimumSize(new Dimension(320, 90));
        scrollPane.setBorder(BorderFactory.createLineBorder(
                new Color(180, 180, 160)));
        // A short result set should end in a clean white table area.  The
        // platform default viewport color was producing a large gray block
        // that looked like unused or missing table rows.
        scrollPane.getViewport().setBackground(Color.WHITE);
        if (scrollPane.getViewport().getView() instanceof JTable) {
            scrollPane.getViewport().getView().setBackground(Color.WHITE);
        }
    }

    private static class TooltipRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean selected, boolean focused, int row, int column) {
            Component component = super.getTableCellRendererComponent(
                    table, value, selected, focused, row, column);
            setToolTipText(value == null ? null : value.toString());
            return component;
        }
    }
}
