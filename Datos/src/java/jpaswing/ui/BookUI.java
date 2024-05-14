package jpaswing.ui;

import jpaswing.controller.BookController;
import jpaswing.entity.Book;
import jpaswing.repository.BookRepository;

import javax.swing.*;
import java.awt.*;

public class BookUI extends JFrame {
    private JTextField idField;
    private JTextField nameField;
    private JPanel panel1;
    private Book book;
    private BookRepository bookRepository;
    private BookRepository secondaryBookRepository;
    private BookController bookController;

    private JButton btnFirst;
    private JButton btnPrevious;
    private JButton btnNext;
    private JButton btnLast;
    private JButton btnSave;
    private JButton btnNew;
    private JButton btnDelete; // Nuevo botón

    private boolean creatingNewRecord = false; // Variable de estado

    // Constructor que acepta dos BookRepository como argumentos
    public BookUI(BookRepository bookRepository, BookRepository secondaryBookRepository) {
        this.bookRepository = bookRepository;
        this.secondaryBookRepository = secondaryBookRepository;
        this.bookController = new BookController(bookRepository);
        setTitle("Book Maintenance");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        initComponents();
        this.book = bookRepository.findFirstByOrderByIdAsc();
        updateData();
    }

    private void updateData() {
        if (this.book != null) {
            idField.setText(Long.toString(this.book.getId()));
            nameField.setText(this.book.getName());
            btnNew.setText("New");
            creatingNewRecord = false;
        } else {
            idField.setText("");
            nameField.setText("");
            btnNew.setText("New");
            creatingNewRecord = true;
        }
    }

    private void initComponents() {
        panel1 = new JPanel(new GridLayout(4, 1));

        idField = new JTextField();
        nameField = new JTextField();

        JPanel fieldPanel = new JPanel(new GridLayout(2, 2));
        fieldPanel.add(new JLabel("ID:"));
        fieldPanel.add(idField);
        fieldPanel.add(new JLabel("Name:"));
        fieldPanel.add(nameField);

        panel1.add(fieldPanel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));

        btnFirst = createButton("<<");
        btnFirst.addActionListener(e -> first());

        btnPrevious = createButton("<");
        btnPrevious.addActionListener(e -> previous());

        btnNext = createButton(">");
        btnNext.addActionListener(e -> next());

        btnLast = createButton(">>");
        btnLast.addActionListener(e -> last());

        buttonPanel.add(btnFirst);
        buttonPanel.add(btnPrevious);
        buttonPanel.add(btnNext);
        buttonPanel.add(btnLast);

        panel1.add(buttonPanel);

        // Add save, new/cancel, and delete buttons
        btnSave = new JButton("Save");
        btnSave.addActionListener(e -> save());

        btnNew = new JButton("New");
        btnNew.addActionListener(e -> toggleNewRecord());

        btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> delete());

        JPanel saveNewPanel = new JPanel(new FlowLayout());
        saveNewPanel.add(btnNew);
        saveNewPanel.add(btnSave);
        saveNewPanel.add(btnDelete);

        panel1.add(saveNewPanel);

        add(panel1);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(50, 25));
        return button;
    }

    private void next() {
        this.book = bookController.next().orElse(null);
        updateData();
    }

    private void previous() {
        this.book = bookController.previous().orElse(null);
        updateData();
    }

    private void last() {
        this.book = bookController.last().orElse(null);
        updateData();
    }

    private void first() {
        this.book = bookController.first().orElse(null);
        updateData();
    }

    private void save() {
        String name = nameField.getText();
        if (!name.isEmpty()) {
            Book bookToSave = new Book(name);
            if (!idField.getText().isEmpty()) {
                bookToSave.setId(Long.parseLong(idField.getText()));
            }
            bookRepository.save(bookToSave);
            this.book = bookToSave;
            updateData();
        } else {
            JOptionPane.showMessageDialog(this, "Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void toggleNewRecord() {
        if (creatingNewRecord) {
            // Cancelar la creación de un nuevo registro
            updateData();
        } else {
            // Iniciar la creación de un nuevo registro
            idField.setText("");
            nameField.setText("");
            btnNew.setText("Cancel");
            creatingNewRecord = true;
        }
    }

    private void delete() {
        if (this.book != null && this.book.getId() != null) {
            bookRepository.deleteById(this.book.getId());
            this.book = bookController.first().orElse(null);
            updateData();
        } else {
            JOptionPane.showMessageDialog(this, "No book selected to delete", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
