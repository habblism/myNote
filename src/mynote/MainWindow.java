package mynote;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.text.StyledEditorKit;

import bean.Note;

import models.JListAdapter;

import components.NoteTextArea;
import components.RowHeaderNumberView;

public class MainWindow {
	private JFrame mJFrame;
	private Container mContainer;
	private JList<Note> mJList;
	private JListAdapter mJListAdapter;
	private JMenuBar mJMenuBar;
	private JTabbedPane mJTabbedPane;
	private JLabel mJLabel;

	public MainWindow() {
		mJFrame = new JFrame();
		initJMenuBar();
		initJList();
		initJTabbedPane();
		initJLabel();

		JSplitPane jSplitPane_tb = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				false, mJTabbedPane, mJLabel);
		jSplitPane_tb.setDividerSize(3);

		JSplitPane jSplitPane_lr = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				false, new JScrollPane(mJList), jSplitPane_tb);
		jSplitPane_lr.setDividerSize(3);

		mContainer = mJFrame.getContentPane();
		mContainer.add(jSplitPane_lr);

		mJFrame.setJMenuBar(mJMenuBar);
		mJFrame.setTitle("MyNote");
		mJFrame.setSize(400 + 600, 600);
		mJFrame.setLocation(50, 50);
		mJFrame.setVisible(true);
		mJFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.exit(1);
			}
		});
	}

	private void initJLabel() {
		mJLabel = new JLabel();
	}

	private void initJTabbedPane() {
		mJTabbedPane = new JTabbedPane();
		mJTabbedPane.setMinimumSize(new Dimension(0, 400));
		mJTabbedPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!(e.getModifiers() == InputEvent.BUTTON3_MASK)) {
					return;
				}
				JPopupMenu jPopupMenu = new JPopupMenu();
				JMenuItem menuItem2 = new JMenuItem("关闭");
				JMenuItem menuItem3 = new JMenuItem("关闭所有");

				menuItem2
						.addActionListener(new java.awt.event.ActionListener() { // 关闭的事件监听
							public void actionPerformed(ActionEvent e) {
								mJTabbedPane.remove(mJTabbedPane
										.getSelectedComponent());
							}
						});
				menuItem3
						.addActionListener(new java.awt.event.ActionListener() { // 关闭所有的事件监听
							public void actionPerformed(ActionEvent e) {
								mJTabbedPane.removeAll();
							}
						});

				jPopupMenu.add(menuItem2);
				jPopupMenu.add(menuItem3);
				jPopupMenu.show(mJTabbedPane, e.getX(), e.getY());

			}
		});
	}

	private void initJList() {
		mJListAdapter = new JListAdapter("E:\\作业文档\\");

		mJList = new JList<Note>(mJListAdapter);
		mJList.setFixedCellHeight(50);
		mJList.setFixedCellWidth(200);
		mJList.setMinimumSize(new Dimension(0, 0));
		mJList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					mJListAdapter.setReturnObject();
					addNoteTab(mJList.getSelectedValue());
				}
			}
		});
		mJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		mJList.setBorder(BorderFactory.createTitledBorder("所有笔记" + "("
				+ mJListAdapter.getWorkplace() + ")"));
	}

	private void initJMenuBar() {
		mJMenuBar = new JMenuBar();

		addJMenu_file();
		addJMenu_edit();
		addJMenu_about();
		addJMenu_setting();
		addJMenu_style();

	}

	private void addJMenu_setting() {
		JMenu jMenu_setting = new JMenu("设置(T)");
		jMenu_setting.setMnemonic('T');

		JMenuItem item_personal_setting = new JMenuItem("个性设置");

		item_personal_setting.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

			}
		});

		jMenu_setting.add(item_personal_setting);
		// jMenu_setting.add(DefaultEditorKit.selectAllAction);

		mJMenuBar.add(jMenu_setting);
	}

	private void addJMenu_about() {
		JMenu jMenu_about = new JMenu("关于(H)");
		jMenu_about.setMnemonic('H');

		JMenuItem item_about_mynote = new JMenuItem("关于MyNote");

		item_about_mynote.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

			}
		});

		jMenu_about.add(item_about_mynote);

		mJMenuBar.add(jMenu_about);
	}

	private void addJMenu_edit() {
		JMenu jMenu_edit = new JMenu("编辑(E)");
		jMenu_edit.setMnemonic('E');

		JMenuItem item_undo = new JMenuItem("撤销");
		JMenuItem item_redo = new JMenuItem("恢复");
		JMenuItem item_cut = new JMenuItem("剪切");
		JMenuItem item_copy = new JMenuItem("复制");
		JMenuItem item_paste = new JMenuItem("黏贴");
		JMenuItem item_delete = new JMenuItem("删除");
		JMenuItem item_select_all = new JMenuItem("全选");

		item_undo.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
		item_redo.setAccelerator(KeyStroke.getKeyStroke("ctrl Y"));
		item_cut.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
		item_copy.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
		item_paste.setAccelerator(KeyStroke.getKeyStroke("ctrl V"));
		item_delete.setAccelerator(KeyStroke.getKeyStroke("ctrl D"));
		item_select_all.setAccelerator(KeyStroke.getKeyStroke("ctrl A"));

		item_undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NoteTextArea nat = (NoteTextArea) ((JScrollPane) mJTabbedPane
						.getSelectedComponent()).getViewport().getView();
				if (nat.undoManager.canUndo()) {
					nat.undoManager.undo();
				}
			}
		});
		item_redo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				NoteTextArea nat = (NoteTextArea) ((JScrollPane) mJTabbedPane
						.getSelectedComponent()).getViewport().getView();
				if (nat.undoManager.canRedo()) {
					nat.undoManager.redo();
				}

			}
		});
		item_cut.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

			}
		});
		item_copy.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		item_paste.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		item_delete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				NoteTextArea nat = (NoteTextArea) ((JScrollPane) mJTabbedPane
						.getSelectedComponent()).getViewport().getView();
				if (nat.getSelectedText() != null
						&& !nat.getSelectedText().equals("")) {
					nat.replaceSelection("");
				}

			}
		});
		item_select_all.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				NoteTextArea nat = (NoteTextArea) ((JScrollPane) mJTabbedPane
						.getSelectedComponent()).getViewport().getView();
				nat.selectAll();

			}
		});

		jMenu_edit.add(item_undo);
		jMenu_edit.add(item_redo);
		jMenu_edit.add(item_cut);
		jMenu_edit.add(item_copy);
		jMenu_edit.add(item_paste);
		jMenu_edit.add(item_delete);
		jMenu_edit.add(item_select_all);

		mJMenuBar.add(jMenu_edit);

	}

	private void addJMenu_file() {
		JMenu jMenu_file = new JMenu("文件(F)");
		jMenu_file.setMnemonic('F');

		JMenuItem item_new = new JMenuItem("新建");
		JMenuItem item_open = new JMenuItem("打开");
		JMenuItem item_store = new JMenuItem("保存");
		JMenuItem item_choose_workplace = new JMenuItem("工作区");

		item_new.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		item_open.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		item_store.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));

		item_store.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JScrollPane scp = (JScrollPane) mJTabbedPane
						.getSelectedComponent();
				Note note = ((NoteTextArea) scp.getViewport().getView())
						.getNote();
				if (note.getFile() != null) {
					FileOutputStream fileOutputStream;
					try {
						fileOutputStream = new FileOutputStream(note
								.getFilePath());
						fileOutputStream.write(((NoteTextArea) scp
								.getViewport().getView()).getText().getBytes());
						fileOutputStream.close();
						System.out.println("保存成功");
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("保存失败");
					}

				}
			}
		});
		item_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileDialog fileDialog = new FileDialog(mJFrame, "选择文件");
				fileDialog.setVisible(true);

				File file = null;
				if (fileDialog.getFile() != null) {
					file = new File(fileDialog.getDirectory(), fileDialog
							.getFile());
					Note note = new Note();
					note.setFilePath(file.getAbsolutePath());
					note.setTitle(file.getName());
					note.setFile(file);

					addNoteTab(note);

					mJListAdapter.addNote(note);
					mJList.setModel(mJListAdapter);
					mJList.updateUI();
				} else {
					System.out.println("文件不存在！");
				}
			}
		});
		item_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String input = JOptionPane.showInputDialog(mJFrame, "输入文本标题：",
						"", JOptionPane.PLAIN_MESSAGE);

				if (input != null) {
					String title = !input.equals("") ? input : "未命名文本";
					Note note = new Note(title + ".txt", mJListAdapter
							.getWorkplace() + title + ".txt");
					addNoteTab(note);

					mJListAdapter.addNote(note);
					mJList.setModel(mJListAdapter);
					mJList.updateUI();

				}

			}
		});
		item_choose_workplace.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setVisible(true);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = chooser.showOpenDialog(mJFrame);
				if (result == JFileChooser.APPROVE_OPTION) {
					mJListAdapter = new JListAdapter(chooser.getSelectedFile()
							.getAbsolutePath());
					mJList.setBorder(BorderFactory.createTitledBorder("所有笔记"
							+ "(" + mJListAdapter.getWorkplace() + ")"));

					mJList.setModel(mJListAdapter);
					mJList.updateUI();
				}

			}
		});

		jMenu_file.add(item_new);
		jMenu_file.add(item_open);
		jMenu_file.add(item_store);
		jMenu_file.add(item_choose_workplace);

		mJMenuBar.add(jMenu_file);
	}

	private void addJMenu_style() {
		JMenu jMenu_style = new JMenu("格式"+"(S)");
		jMenu_style.setMnemonic('S');

		Action action = new StyledEditorKit.BoldAction();
		action.putValue(Action.NAME, "加粗");
		action.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("ctrl B"));
		jMenu_style.add(action);

		action = new StyledEditorKit.UnderlineAction();
		action.putValue(Action.NAME, "下划线");
		action.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("ctrl U"));
		jMenu_style.add(action);

		action = new StyledEditorKit.ItalicAction();
		action.putValue(Action.NAME, "倾斜");
		action.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("ctrl I"));
		jMenu_style.add(action);

		jMenu_style.addSeparator();

		jMenu_style.add(new StyledEditorKit.FontSizeAction("12", 12));
		jMenu_style.add(new StyledEditorKit.FontSizeAction("14", 14));
		jMenu_style.add(new StyledEditorKit.FontSizeAction("18", 18));

		jMenu_style.addSeparator();

		jMenu_style.add(new StyledEditorKit.ForegroundAction("Red", Color.red));
		jMenu_style.add(new StyledEditorKit.ForegroundAction("Green", Color.green));
		jMenu_style.add(new StyledEditorKit.ForegroundAction("Blue", Color.blue));
		jMenu_style.add(new StyledEditorKit.ForegroundAction("Black", Color.black));

		mJMenuBar.add(jMenu_style);
	}

	private void addNoteTab(Note note) {
		NoteTextArea nTextArea = new NoteTextArea(note);
		JScrollPane jsp = new JScrollPane(nTextArea);
		jsp.setRowHeaderView(new RowHeaderNumberView(nTextArea));
		mJTabbedPane.addTab(nTextArea.getNote().getTitle(), jsp);
		mJTabbedPane.setSelectedComponent(jsp);
	}
}
