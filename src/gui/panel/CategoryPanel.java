// 
// Decompiled by Procyon v0.5.30
// 

package gui.panel;

import java.awt.event.ActionListener;
import gui.listener.CategoryListener;
import service.CategoryService;
import entity.Category;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.JComponent;
import util.ColorUtil;
import javax.swing.table.TableModel;
import util.GUIUtil;
import javax.swing.JTable;
import gui.model.CategoryTableModel;
import javax.swing.JButton;

public class CategoryPanel extends WorkingPanel
{
    public static CategoryPanel instance;
    public JButton bAdd;
    public JButton bEdit;
    public JButton bDelete;
    String[] columNames;
    public CategoryTableModel ctm;
    public JTable t;
    
    static {
        GUIUtil.useLNF();
        CategoryPanel.instance = new CategoryPanel();
    }
    
    public CategoryPanel() {
        this.bAdd = new JButton("\u65b0\u589e");
        this.bEdit = new JButton("\u7f16\u8f91");
        this.bDelete = new JButton("\u5220\u9664");
        this.columNames = new String[] { "\u5206\u7c7b\u540d\u79f0", "\u6d88\u8d39\u6b21\u6570" };
        this.ctm = new CategoryTableModel();
        this.t = new JTable(this.ctm);
        GUIUtil.setColor(ColorUtil.blueColor, this.bAdd, this.bEdit, this.bDelete);
        final JScrollPane sp = new JScrollPane(this.t);
        final JPanel pSubmit = new JPanel();
        pSubmit.add(this.bAdd);
        pSubmit.add(this.bEdit);
        pSubmit.add(this.bDelete);
        this.setLayout(new BorderLayout());
        this.add(sp, "Center");
        this.add(pSubmit, "South");
        this.addListener();
    }
    
    public static void main(final String[] args) {
        GUIUtil.showPanel(CategoryPanel.instance);
    }
    
    public Category getSelectedCategory() {
        final int index = this.t.getSelectedRow();
        return this.ctm.cs.get(index);
    }
    
    @Override
    public void updateData() {
        this.ctm.cs = new CategoryService().list();
        this.t.updateUI();
        this.t.getSelectionModel().setSelectionInterval(0, 0);
        if (this.ctm.cs.size() == 0) {
            this.bEdit.setEnabled(false);
            this.bDelete.setEnabled(false);
        }
        else {
            this.bEdit.setEnabled(true);
            this.bDelete.setEnabled(true);
        }
    }
    
    @Override
    public void addListener() {
        final CategoryListener listener = new CategoryListener();
        this.bAdd.addActionListener(listener);
        this.bEdit.addActionListener(listener);
        this.bDelete.addActionListener(listener);
    }
}
