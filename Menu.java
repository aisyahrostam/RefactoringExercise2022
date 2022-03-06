import java.awt.*;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import java.util.Date;

public class Menu extends JFrame {

	private ArrayList<Customer> customerList = new ArrayList<Customer>();
	private int position = 0;
	private String password;
	private Customer customer = null;
	private CustomerAccount acc = new CustomerAccount();
	JFrame f, f1;
	JLabel fNameLbl, surnameLabel, pPPSLabel, dOBLabel, customerIDLabel, passwordLabel;
	JTextField fNameTxtFld, surnameTextField, pPSTextField, dOBTextField, customerIDTextField, passwordTextField;
	Container content;
	Customer e;

	JPanel panel2;
	JButton addBtn;
	String PPS, fName, surname, DOB, CustomerID;

	public static void main(String[] args) {
		Menu driver = new Menu();
		driver.menuStart();
	}

	public void menuStart() {

		f = new JFrame("User Type");
		f.setSize(400, 300);
		f.setLocation(200, 200);
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});

		JPanel userTypePanel = new JPanel();
		final ButtonGroup userType = new ButtonGroup();
		JRadioButton radioBtn;
		userTypePanel.add(radioBtn = new JRadioButton("Existing Customer"));
		radioBtn.setActionCommand("Customer");
		userType.add(radioBtn);

		userTypePanel.add(radioBtn = new JRadioButton("Administrator"));
		radioBtn.setActionCommand("Administrator");
		userType.add(radioBtn);

		userTypePanel.add(radioBtn = new JRadioButton("New Customer"));
		radioBtn.setActionCommand("New Customer");
		userType.add(radioBtn);

		JPanel continuePanel = new JPanel();
		JButton continueBtn = new JButton("Continue");
		continuePanel.add(continueBtn);

		Container content = f.getContentPane();
		content.setLayout(new GridLayout(2, 1));
		content.add(userTypePanel);
		content.add(continuePanel);

		continueBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String user = userType.getSelection().getActionCommand();

				if (user.equals("New Customer")) {
					f.dispose();
					f1 = new JFrame("Create New Customer");
					f1.setSize(400, 300);
					f1.setLocation(200, 200);
					f1.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent we) {
							System.exit(0);
						}
					});
					Container content = f1.getContentPane();
					content.setLayout(new BorderLayout());

					fNameLbl = new JLabel("First Name:", SwingConstants.RIGHT);
					surnameLabel = new JLabel("Surname:", SwingConstants.RIGHT);
					pPPSLabel = new JLabel("PPS Number:", SwingConstants.RIGHT);
					dOBLabel = new JLabel("Date of birth", SwingConstants.RIGHT);
					fNameTxtFld = new JTextField(20);
					surnameTextField = new JTextField(20);
					pPSTextField = new JTextField(20);
					dOBTextField = new JTextField(20);
					JPanel panel = new JPanel(new GridLayout(6, 2));
					panel.add(fNameLbl);
					panel.add(fNameTxtFld);
					panel.add(surnameLabel);
					panel.add(surnameTextField);
					panel.add(pPPSLabel);
					panel.add(pPSTextField);
					panel.add(dOBLabel);
					panel.add(dOBTextField);

					panel2 = new JPanel();
					addBtn = new JButton("Add");

					addBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

							PPS = pPSTextField.getText();
							fName = fNameTxtFld.getText();
							surname = surnameTextField.getText();
							DOB = dOBTextField.getText();
							password = "";

							CustomerID = "ID" + PPS;

							addBtn.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									f1.dispose();

									boolean loop = true;
									while (loop) {
										password = JOptionPane.showInputDialog(f, "Enter 7 character Password;");

										if (password.length() != 7) {
											JOptionPane.showMessageDialog(null, null,
													"Password must be 7 charatcers long", JOptionPane.OK_OPTION);
										} else {
											 loop = false;
										 }
									}

									ArrayList<CustomerAccount> accounts = new ArrayList<CustomerAccount>();
									Customer customer = new Customer(PPS, surname, fName, DOB, CustomerID, password,
											accounts);

									customerList.add(customer);

									JOptionPane.showMessageDialog(f,
											"CustomerID = " + CustomerID + "\n Password = " + password,
											"Customer created.", JOptionPane.INFORMATION_MESSAGE);
									menuStart();
									f.dispose();
								}
							});
						}
					});
					JButton cancelBtn = new JButton("Cancel");
					cancelBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							f1.dispose();
							menuStart();
						}
					});

					panel2.add(addBtn);
					panel2.add(cancelBtn);

					content.add(panel, BorderLayout.CENTER);
					content.add(panel2, BorderLayout.SOUTH);

					f1.setVisible(true);

				}

				if (user.equals("Administrator")) {
					boolean loop = true;
					boolean cont = false;
					while (loop) {
						Object adminUsername = JOptionPane.showInputDialog(f, "Enter Administrator Username:");

						if (!adminUsername.equals("admin")) {
							int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect Username. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {
								loop = true;
							} else if (reply == JOptionPane.NO_OPTION) {
								f1.dispose();
								loop = false;
								menuStart();
							}
						}
					}

					while (loop) {
						Object adminPassword = JOptionPane.showInputDialog(f, "Enter Administrator Password;");

						if (!adminPassword.equals("admin11")) {
							int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect Password. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {

							} else if (reply == JOptionPane.NO_OPTION) {
								f1.dispose();
								loop = false;
								menuStart();
							}
						}
					}

					if (cont) {
						f1.dispose();
						loop = false;
						admin();
					}
				}

				if (user.equals("Customer")) {
					boolean loop = true;
					boolean cont = false;
					boolean found = false;
					Customer customer = null;
					while (loop) {
						Object customerID = JOptionPane.showInputDialog(f, "Enter Customer ID:");

						for (Customer aCustomer : customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								customer = aCustomer;
							}
						}

						if (found == false) {
							int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {
								loop = true;
							} else if (reply == JOptionPane.NO_OPTION) {
								f.dispose();
								loop = false;
								menuStart();
							}
						}

					}

					while (loop) {
						Object customerPassword = JOptionPane.showInputDialog(f, "Enter Customer Password;");

						if (!customer.getPassword().equals(customerPassword)) {
							int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect password. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {

							} else if (reply == JOptionPane.NO_OPTION) {
								f.dispose();
								loop = false;
								menuStart();
							}
						}
					}

					if (cont) {
						f.dispose();
						loop = false;
						customer();
					}
				}

			}
		});
		f.setVisible(true);
	}

	public void admin() {
		dispose();

		f = new JFrame("Administrator Menu");
		f.setSize(400, 400);
		f.setLocation(200, 200);
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		f.setVisible(true);

		JPanel deleteCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton deleteCustomerBtn = new JButton("Delete Customer");
		deleteCustomerBtn.setPreferredSize(new Dimension(250, 20));
		deleteCustomerPanel.add(deleteCustomerBtn);

		JPanel deleteAccountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton deleteAccountBtn = new JButton("Delete Account");
		deleteAccountBtn.setPreferredSize(new Dimension(250, 20));
		deleteAccountPanel.add(deleteAccountBtn);

		JPanel bankChargesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton bankChargesBtn = new JButton("Apply Bank Charges");
		bankChargesBtn.setPreferredSize(new Dimension(250, 20));
		bankChargesPanel.add(bankChargesBtn);

		JPanel interestPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton interestBtn = new JButton("Apply Interest");
		interestPanel.add(interestBtn);
		interestBtn.setPreferredSize(new Dimension(250, 20));

		JPanel editCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton editCustomerBtn = new JButton("Edit existing Customer");
		editCustomerPanel.add(editCustomerBtn);
		editCustomerBtn.setPreferredSize(new Dimension(250, 20));

		JPanel navigatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton navigateBtn = new JButton("Navigate Customer Collection");
		navigatePanel.add(navigateBtn);
		navigateBtn.setPreferredSize(new Dimension(250, 20));

		JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton summaryBtn = new JButton("Display Summary Of All Accounts");
		summaryPanel.add(summaryBtn);
		summaryBtn.setPreferredSize(new Dimension(250, 20));

		JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton accountBtn = new JButton("Add an Account to a Customer");
		accountPanel.add(accountBtn);
		accountBtn.setPreferredSize(new Dimension(250, 20));

		JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton returnBtn = new JButton("Exit Admin Menu");
		returnPanel.add(returnBtn);

		JLabel label1 = new JLabel("Please select an option");

		content = f.getContentPane();
		content.setLayout(new GridLayout(9, 1));
		content.add(label1);
		content.add(accountPanel);
		content.add(bankChargesPanel);
		content.add(interestPanel);
		content.add(editCustomerPanel);
		content.add(navigatePanel);
		content.add(summaryPanel);
		content.add(deleteCustomerPanel);
		content.add(returnPanel);

		bankChargesBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				boolean loop = true;

				boolean found = false;

				if (customerList.isEmpty()) {
					JOptionPane.showMessageDialog(f, "There are no customers yet!", "Oops!",
							JOptionPane.INFORMATION_MESSAGE);
					f.dispose();
					admin();

				} else {
					while (loop) {
						Object customerID = JOptionPane.showInputDialog(f,
								"Customer ID of Customer You Wish to Apply Charges to:");

						for (Customer aCustomer : customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								customer = aCustomer;
								loop = false;
							}
						}

						if (found == false) {
							int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {
								loop = true;
							} else if (reply == JOptionPane.NO_OPTION) {
								f.dispose();
								loop = false;

								admin();
							}
						} else {
							f.dispose();
							f = new JFrame("Administrator Menu");
							f.setSize(400, 300);
							f.setLocation(200, 200);
							f.addWindowListener(new WindowAdapter() {
								@Override
								public void windowClosing(WindowEvent we) {
									System.exit(0);
								}
							});
							f.setVisible(true);

							JComboBox<String> box = new JComboBox<String>();
							for (int i = 0; i < customer.getAccounts().size(); i++) {

								box.addItem(customer.getAccounts().get(i).getNumber());
							}

							box.getSelectedItem();

							JPanel boxPanel = new JPanel();
							boxPanel.add(box);

							JPanel buttonPanel = new JPanel();
							JButton continueBtn = new JButton("Apply Charge");
							JButton returnBtn = new JButton("Return");
							buttonPanel.add(continueBtn);
							buttonPanel.add(returnBtn);
							Container content = f.getContentPane();
							content.setLayout(new GridLayout(2, 1));

							content.add(boxPanel);
							content.add(buttonPanel);

							if (customer.getAccounts().isEmpty()) {
								JOptionPane.showMessageDialog(f,
										"This customer has no accounts! \n The admin must add acounts to this customer.",
										"Oops!", JOptionPane.INFORMATION_MESSAGE);
								f.dispose();
								admin();
							} else {

								for (int i = 0; i < customer.getAccounts().size(); i++) {
									if (customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
										acc = customer.getAccounts().get(i);
									}
								}

								continueBtn.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent ae) {
										String euro = "\u20ac";

										if (acc instanceof CustomerDepositAccount) {

											JOptionPane.showMessageDialog(f,
													"25" + euro + " deposit account fee aplied.", "",
													JOptionPane.INFORMATION_MESSAGE);
											acc.setBalance(acc.getBalance() - 25);
											JOptionPane.showMessageDialog(f, "New balance = " + acc.getBalance(),
													"Success!", JOptionPane.INFORMATION_MESSAGE);
										}

										if (acc instanceof CustomerCurrentAccount) {

											JOptionPane.showMessageDialog(f,
													"15" + euro + " current account fee aplied.", "",
													JOptionPane.INFORMATION_MESSAGE);
											acc.setBalance(acc.getBalance() - 25);
											JOptionPane.showMessageDialog(f, "New balance = " + acc.getBalance(),
													"Success!", JOptionPane.INFORMATION_MESSAGE);
										}

										f.dispose();
										admin();
									}
								});

								returnBtn.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent ae) {
										f.dispose();
										menuStart();
									}
								});

							}
						}
					}
				}

			}
		});

		interestBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				boolean loop = true;

				boolean found = false;

				if (customerList.isEmpty()) {
					JOptionPane.showMessageDialog(f, "There are no customers yet!", "Oops!",
							JOptionPane.INFORMATION_MESSAGE);
					f.dispose();
					admin();

				} else {
					while (loop) {
						Object customerID = JOptionPane.showInputDialog(f,
								"Customer ID of Customer You Wish to Apply Interest to:");

						for (Customer aCustomer : customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								customer = aCustomer;
								loop = false;
							}
						}

						if (found == false) {
							int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {
								loop = true;
							} else if (reply == JOptionPane.NO_OPTION) {
								f.dispose();
								loop = false;

								admin();
							}
						} else {
							f.dispose();
							f = new JFrame("Administrator Menu");
							f.setSize(400, 300);
							f.setLocation(200, 200);
							f.addWindowListener(new WindowAdapter() {
								@Override
								public void windowClosing(WindowEvent we) {
									System.exit(0);
								}
							});
							f.setVisible(true);

							JComboBox<String> box = new JComboBox<String>();
							for (int i = 0; i < customer.getAccounts().size(); i++) {

								box.addItem(customer.getAccounts().get(i).getNumber());
							}

							box.getSelectedItem();

							JPanel boxPanel = new JPanel();

							JLabel label = new JLabel("Select an account to apply interest to:");
							boxPanel.add(label);
							boxPanel.add(box);
							JPanel buttonPanel = new JPanel();
							JButton continueBtn = new JButton("Apply Interest");
							JButton returnBtn = new JButton("Return");
							buttonPanel.add(continueBtn);
							buttonPanel.add(returnBtn);
							Container content = f.getContentPane();
							content.setLayout(new GridLayout(2, 1));

							content.add(boxPanel);
							content.add(buttonPanel);

							if (customer.getAccounts().isEmpty()) {
								JOptionPane.showMessageDialog(f,
										"This customer has no accounts! \n The admin must add acounts to this customer.",
										"Oops!", JOptionPane.INFORMATION_MESSAGE);
								f.dispose();
								admin();
							} else {

								for (int i = 0; i < customer.getAccounts().size(); i++) {
									if (customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
										acc = customer.getAccounts().get(i);
									}
								}

								continueBtn.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent ae) {
										String euro = "\u20ac";
										double interest = 0;
										boolean loop = true;

										while (loop) {
											String interestString = JOptionPane.showInputDialog(f,
													"Enter interest percentage you wish to apply: \n NOTE: Please enter a numerical value. (with no percentage sign) \n E.g: If you wish to apply 8% interest, enter '8'");
											if (isStringNumeric(interestString)) {

												interest = Double.parseDouble(interestString);
												loop = false;

												acc.setBalance(
														acc.getBalance() + (acc.getBalance() * (interest / 100)));

												JOptionPane.showMessageDialog(f,
														interest + "% interest applied. \n new balance = "
																+ acc.getBalance() + euro,
														"Success!", JOptionPane.INFORMATION_MESSAGE);
											}

											else {
												JOptionPane.showMessageDialog(f, "You must enter a numerical value!",
														"Oops!", JOptionPane.INFORMATION_MESSAGE);
											}

										}

										f.dispose();
										admin();
									}
								});

								returnBtn.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent ae) {
										f.dispose();
										menuStart();
									}
								});

							}
						}
					}
				}

			}
		});

		editCustomerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				boolean loop = true;

				boolean found = false;

				if (customerList.isEmpty()) {
					JOptionPane.showMessageDialog(f, "There are no customers yet!", "Oops!",
							JOptionPane.INFORMATION_MESSAGE);
					f.dispose();
					admin();

				} else {

					while (loop) {
						Object customerID = JOptionPane.showInputDialog(f, "Enter Customer ID:");

						for (Customer aCustomer : customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								customer = aCustomer;
							}
						}

						if (found == false) {
							int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {
								loop = true;
							} else if (reply == JOptionPane.NO_OPTION) {
								f.dispose();
								loop = false;

								admin();
							}
						}

					}

					f.dispose();

					f.dispose();
					f = new JFrame("Administrator Menu");
					f.setSize(400, 300);
					f.setLocation(200, 200);
					f.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent we) {
							System.exit(0);
						}
					});

					fNameLbl = new JLabel("First Name:", SwingConstants.LEFT);
					surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
					pPPSLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
					dOBLabel = new JLabel("Date of birth", SwingConstants.LEFT);
					customerIDLabel = new JLabel("CustomerID:", SwingConstants.LEFT);
					passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
					fNameTxtFld = new JTextField(20);
					surnameTextField = new JTextField(20);
					pPSTextField = new JTextField(20);
					dOBTextField = new JTextField(20);
					customerIDTextField = new JTextField(20);
					passwordTextField = new JTextField(20);

					JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

					JPanel cancelPanel = new JPanel();

					textPanel.add(fNameLbl);
					textPanel.add(fNameTxtFld);
					textPanel.add(surnameLabel);
					textPanel.add(surnameTextField);
					textPanel.add(pPPSLabel);
					textPanel.add(pPSTextField);
					textPanel.add(dOBLabel);
					textPanel.add(dOBTextField);
					textPanel.add(customerIDLabel);
					textPanel.add(customerIDTextField);
					textPanel.add(passwordLabel);
					textPanel.add(passwordTextField);

					fNameTxtFld.setText(customer.getFirstName());
					surnameTextField.setText(customer.getSurname());
					pPSTextField.setText(customer.getPPS());
					dOBTextField.setText(customer.getDOB());
					customerIDTextField.setText(customer.getCustomerID());
					passwordTextField.setText(customer.getPassword());

					JButton saveButton = new JButton("Save");
					JButton cancelBtn = new JButton("Exit");

					cancelPanel.add(cancelBtn, BorderLayout.SOUTH);
					cancelPanel.add(saveButton, BorderLayout.SOUTH);
					Container content = f.getContentPane();
					content.setLayout(new GridLayout(2, 1));
					content.add(textPanel, BorderLayout.NORTH);
					content.add(cancelPanel, BorderLayout.SOUTH);

					f.setContentPane(content);
					f.setSize(340, 350);
					f.setLocation(200, 200);
					f.setVisible(true);
					f.setResizable(false);

					saveButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent ae) {

							customer.setFirstName(fNameTxtFld.getText());
							customer.setSurname(surnameTextField.getText());
							customer.setPPS(pPSTextField.getText());
							customer.setDOB(dOBTextField.getText());
							customer.setCustomerID(customerIDTextField.getText());
							customer.setPassword(passwordTextField.getText());

							JOptionPane.showMessageDialog(null, "Changes Saved.");
						}
					});

					cancelBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent ae) {
							f.dispose();

							admin();
						}
					});
				}
			}
		});

		summaryBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				f.dispose();

				f = new JFrame("Summary of Transactions");
				f.setSize(400, 700);
				f.setLocation(200, 200);
				f.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent we) {
						System.exit(0);
					}
				});
				f.setVisible(true);

				JLabel label1 = new JLabel("Summary of all transactions: ");

				JPanel returnPanel = new JPanel();
				JButton returnBtn = new JButton("Return");
				returnPanel.add(returnBtn);

				JPanel textPanel = new JPanel();

				textPanel.setLayout(new BorderLayout());
				JTextArea textArea = new JTextArea(40, 20);
				textArea.setEditable(false);
				textPanel.add(label1, BorderLayout.NORTH);
				textPanel.add(textArea, BorderLayout.CENTER);
				textPanel.add(returnBtn, BorderLayout.SOUTH);

				JScrollPane scrollPane = new JScrollPane(textArea);
				textPanel.add(scrollPane);

				for (int a = 0; a < customerList.size(); a++) {
					for (int b = 0; b < customerList.get(a).getAccounts().size(); b++) {
						acc = customerList.get(a).getAccounts().get(b);
						for (int c = 0; c < customerList.get(a).getAccounts().get(b).getTransactionList().size(); c++) {

							textArea.append(acc.getTransactionList().get(c).toString());

						}
					}
				}

				textPanel.add(textArea);
				content.removeAll();

				Container content = f.getContentPane();
				content.setLayout(new GridLayout(1, 1));
				content.add(textPanel);

				returnBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						f.dispose();
						admin();
					}
				});
			}
		});

		navigateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				f.dispose();

				if (customerList.isEmpty()) {
					JOptionPane.showMessageDialog(null, "There are currently no customers to display. ");
					admin();
				} else {

					JButton firstBtn, previousBtn, nextBtn, lastBtn, cancelBtn;
					JPanel gridPanel, buttonPanel, cancelPanel;

					Container content = getContentPane();

					content.setLayout(new BorderLayout());

					buttonPanel = new JPanel();
					gridPanel = new JPanel(new GridLayout(8, 2));
					cancelPanel = new JPanel();

					fNameLbl = new JLabel("First Name:", SwingConstants.LEFT);
					surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
					pPPSLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
					dOBLabel = new JLabel("Date of birth", SwingConstants.LEFT);
					customerIDLabel = new JLabel("CustomerID:", SwingConstants.LEFT);
					passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
					fNameTxtFld = new JTextField(20);
					surnameTextField = new JTextField(20);
					pPSTextField = new JTextField(20);
					dOBTextField = new JTextField(20);
					customerIDTextField = new JTextField(20);
					passwordTextField = new JTextField(20);

					firstBtn = new JButton("First");
					previousBtn = new JButton("Previous");
					nextBtn = new JButton("Next");
					lastBtn = new JButton("Last");
					cancelBtn = new JButton("Cancel");

					fNameTxtFld.setText(customerList.get(0).getFirstName());
					surnameTextField.setText(customerList.get(0).getSurname());
					pPSTextField.setText(customerList.get(0).getPPS());
					dOBTextField.setText(customerList.get(0).getDOB());
					customerIDTextField.setText(customerList.get(0).getCustomerID());
					passwordTextField.setText(customerList.get(0).getPassword());

					fNameTxtFld.setEditable(false);
					surnameTextField.setEditable(false);
					pPSTextField.setEditable(false);
					dOBTextField.setEditable(false);
					customerIDTextField.setEditable(false);
					passwordTextField.setEditable(false);

					gridPanel.add(fNameLbl);
					gridPanel.add(fNameTxtFld);
					gridPanel.add(surnameLabel);
					gridPanel.add(surnameTextField);
					gridPanel.add(pPPSLabel);
					gridPanel.add(pPSTextField);
					gridPanel.add(dOBLabel);
					gridPanel.add(dOBTextField);
					gridPanel.add(customerIDLabel);
					gridPanel.add(customerIDTextField);
					gridPanel.add(passwordLabel);
					gridPanel.add(passwordTextField);

					buttonPanel.add(firstBtn);
					buttonPanel.add(previousBtn);
					buttonPanel.add(nextBtn);
					buttonPanel.add(lastBtn);

					cancelPanel.add(cancelBtn);

					content.add(gridPanel, BorderLayout.NORTH);
					content.add(buttonPanel, BorderLayout.CENTER);
					content.add(cancelPanel, BorderLayout.AFTER_LAST_LINE);
					firstBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent ae) {
							position = 0;
							fNameTxtFld.setText(customerList.get(0).getFirstName());
							surnameTextField.setText(customerList.get(0).getSurname());
							pPSTextField.setText(customerList.get(0).getPPS());
							dOBTextField.setText(customerList.get(0).getDOB());
							customerIDTextField.setText(customerList.get(0).getCustomerID());
							passwordTextField.setText(customerList.get(0).getPassword());
						}
					});

					previousBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent ae) {

							if (position < 1) {
								// don't do anything
							} else {
								position = position - 1;

								fNameTxtFld.setText(customerList.get(position).getFirstName());
								surnameTextField.setText(customerList.get(position).getSurname());
								pPSTextField.setText(customerList.get(position).getPPS());
								dOBTextField.setText(customerList.get(position).getDOB());
								customerIDTextField.setText(customerList.get(position).getCustomerID());
								passwordTextField.setText(customerList.get(position).getPassword());
							}
						}
					});

					nextBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent ae) {

							if (position == customerList.size() - 1) {
								// don't do anything
							} else {
								position = position + 1;

								fNameTxtFld.setText(customerList.get(position).getFirstName());
								surnameTextField.setText(customerList.get(position).getSurname());
								pPSTextField.setText(customerList.get(position).getPPS());
								dOBTextField.setText(customerList.get(position).getDOB());
								customerIDTextField.setText(customerList.get(position).getCustomerID());
								passwordTextField.setText(customerList.get(position).getPassword());
							}

						}
					});

					lastBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent ae) {

							position = customerList.size() - 1;

							fNameTxtFld.setText(customerList.get(position).getFirstName());
							surnameTextField.setText(customerList.get(position).getSurname());
							pPSTextField.setText(customerList.get(position).getPPS());
							dOBTextField.setText(customerList.get(position).getDOB());
							customerIDTextField.setText(customerList.get(position).getCustomerID());
							passwordTextField.setText(customerList.get(position).getPassword());
						}
					});

					cancelBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent ae) {
							dispose();
							admin();
						}
					});
					setContentPane(content);
					setSize(400, 300);
					setVisible(true);
				}
			}
		});

		accountBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				f.dispose();

				if (customerList.isEmpty()) {
					JOptionPane.showMessageDialog(f, "There are no customers yet!", "Oops!",
							JOptionPane.INFORMATION_MESSAGE);
					f.dispose();
					admin();
				} else {
					boolean loop = true;

					boolean found = false;

					while (loop) {
						Object customerID = JOptionPane.showInputDialog(f,
								"Customer ID of Customer You Wish to Add an Account to:");

						for (Customer aCustomer : customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								customer = aCustomer;
							}
						}

						if (found == false) {
							int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {
								loop = true;
							} else if (reply == JOptionPane.NO_OPTION) {
								f.dispose();
								loop = false;

								admin();
							}
						} else {
							loop = false;

							String[] choices = { "Current Account", "Deposit Account" };
							String account = (String) JOptionPane.showInputDialog(null, "Please choose account type",
									"Account Type", JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);

							if (account.equals("Current Account")) {
								boolean valid = true;
								double balance = 0;
								String number = String.valueOf("C" + (customerList.indexOf(customer) + 1) * 10
										+ (customer.getAccounts().size() + 1));
								ArrayList<AccountTransaction> transactionList = new ArrayList<AccountTransaction>();
								int randomPIN = (int) (Math.random() * 9000) + 1000;
								String pin = String.valueOf(randomPIN);

								ATMCard atm = new ATMCard(randomPIN, valid);

								CustomerCurrentAccount current = new CustomerCurrentAccount(atm, number, balance,
										transactionList);

								customer.getAccounts().add(current);
								JOptionPane.showMessageDialog(f, "Account number = " + number + "\n PIN = " + pin,
										"Account created.", JOptionPane.INFORMATION_MESSAGE);

								f.dispose();
								admin();
							}

							if (account.equals("Deposit Account")) {

								double balance = 0, interest = 0;
								String number = String.valueOf("D" + (customerList.indexOf(customer) + 1) * 10
										+ (customer.getAccounts().size() + 1));
								ArrayList<AccountTransaction> transactionList = new ArrayList<AccountTransaction>();

								CustomerDepositAccount deposit = new CustomerDepositAccount(interest, number, balance,
										transactionList);

								customer.getAccounts().add(deposit);
								JOptionPane.showMessageDialog(f, "Account number = " + number, "Account created.",
										JOptionPane.INFORMATION_MESSAGE);

								f.dispose();
								admin();
							}

						}
					}
				}
			}
		});

		deleteCustomerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				boolean found = true, loop = true;

				if (customerList.isEmpty()) {
					JOptionPane.showMessageDialog(null, "There are currently no customers to display. ");
					dispose();
					admin();
				} else {
					{
						Object customerID = JOptionPane.showInputDialog(f,
								"Customer ID of Customer You Wish to Delete:");

						for (Customer aCustomer : customerList) {

							if (aCustomer.getCustomerID().equals(customerID)) {
								found = true;
								customer = aCustomer;
								loop = false;
							}
						}

						if (found == false) {
							int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
									JOptionPane.YES_NO_OPTION);
							if (reply == JOptionPane.YES_OPTION) {
								loop = true;
							} else if (reply == JOptionPane.NO_OPTION) {
								f.dispose();
								loop = false;

								admin();
							}
						} else {
							if (customer.getAccounts().size() > 0) {
								JOptionPane.showMessageDialog(f,
										"This customer has accounts. \n You must delete a customer's accounts before deleting a customer ",
										"Oops!", JOptionPane.INFORMATION_MESSAGE);
							} else {
								customerList.remove(customer);
								JOptionPane.showMessageDialog(f, "Customer Deleted ", "Success.",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}

					}
				}
			}
		});

		deleteAccountBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				boolean found = true, loop = true;

				{
					Object customerID = JOptionPane.showInputDialog(f,
							"Customer ID of Customer from which you wish to delete an account");

					for (Customer aCustomer : customerList) {

						if (aCustomer.getCustomerID().equals(customerID)) {
							found = true;
							customer = aCustomer;
							loop = false;
						}
					}

					if (found == false) {
						int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?",
								JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
							loop = true;
						} else if (reply == JOptionPane.NO_OPTION) {
							f.dispose();
							loop = false;

							admin();
						}
					}

				}
			}

		});
		returnBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				f.dispose();
				menuStart();
			}
		});
	}

	public void customer()
	{	
		f = new JFrame("Customer Menu");
		f.setSize(400, 300);
		f.setLocation(200, 200);
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		f.setVisible(true);

		if (e.getAccounts().size() == 0) {
			JOptionPane.showMessageDialog(f,
					"This customer does not have any accounts yet. \n An admin must create an account for this customer \n for them to be able to use customer functionality. ",
					"Oops!", JOptionPane.INFORMATION_MESSAGE);
			f.dispose();
			menuStart();
		} else {
			JPanel buttonPanel = new JPanel();
			JPanel boxPanel = new JPanel();
			JPanel labelPanel = new JPanel();

			JLabel label = new JLabel("Select Account:");
			labelPanel.add(label);

			JButton returnBtn = new JButton("Return");
			buttonPanel.add(returnBtn);
			JButton continueBtn = new JButton("Continue");
			buttonPanel.add(continueBtn);

			JComboBox<String> box = new JComboBox<String>();
			for (int i = 0; i < e.getAccounts().size(); i++) {
				box.addItem(e.getAccounts().get(i).getNumber());
			}

			for (int i = 0; i < e.getAccounts().size(); i++) {
				if (e.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
					acc = e.getAccounts().get(i);
				}
			}

			boxPanel.add(box);
			content = f.getContentPane();
			content.setLayout(new GridLayout(3, 1));
			content.add(labelPanel);
			content.add(boxPanel);
			content.add(buttonPanel);

			returnBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					f.dispose();
					menuStart();
				}
			});

			continueBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {

					f.dispose();

					f = new JFrame("Customer Menu");
					f.setSize(400, 300);
					f.setLocation(200, 200);
					f.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent we) {
							System.exit(0);
						}
					});
					f.setVisible(true);

					JPanel statementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
					JButton statementBtn = new JButton("Display Bank Statement");
					statementBtn.setPreferredSize(new Dimension(250, 20));

					statementPanel.add(statementBtn);

					JPanel lodgementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
					JButton lodgementBtn = new JButton("Lodge money into account");
					lodgementPanel.add(lodgementBtn);
					lodgementBtn.setPreferredSize(new Dimension(250, 20));

					JPanel withdrawalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
					JButton withdrawBtn = new JButton("Withdraw money from account");
					withdrawalPanel.add(withdrawBtn);
					withdrawBtn.setPreferredSize(new Dimension(250, 20));

					JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
					JButton returnBtn = new JButton("Exit Customer Menu");
					returnPanel.add(returnBtn);

					JLabel label1 = new JLabel("Please select an option");

					content = f.getContentPane();
					content.setLayout(new GridLayout(5, 1));
					content.add(label1);
					content.add(statementPanel);
					content.add(lodgementPanel);
					content.add(withdrawalPanel);
					content.add(returnPanel);

					statementBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent ae) {
							f.dispose();
							f = new JFrame("Customer Menu");
							f.setSize(400, 600);
							f.setLocation(200, 200);
							f.addWindowListener(new WindowAdapter() {
								@Override
								public void windowClosing(WindowEvent we) {
									System.exit(0);
								}
							});
							f.setVisible(true);

							JLabel label1 = new JLabel("Summary of account transactions: ");

							JPanel returnPanel = new JPanel();
							JButton returnBtn = new JButton("Return");
							returnPanel.add(returnBtn);

							JPanel textPanel = new JPanel();

							textPanel.setLayout(new BorderLayout());
							JTextArea textArea = new JTextArea(40, 20);
							textArea.setEditable(false);
							textPanel.add(label1, BorderLayout.NORTH);
							textPanel.add(textArea, BorderLayout.CENTER);
							textPanel.add(returnBtn, BorderLayout.SOUTH);

							JScrollPane scrollPane = new JScrollPane(textArea);
							textPanel.add(scrollPane);

							for (int i = 0; i < acc.getTransactionList().size(); i++) {
								textArea.append(acc.getTransactionList().get(i).toString());

							}

							textPanel.add(textArea);
							content.removeAll();

							Container content = f.getContentPane();
							content.setLayout(new GridLayout(1, 1));
							content.add(textPanel);
							returnBtn.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent ae) {
									f.dispose();
									customer();
								}
							});
						}
					});

					lodgementBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent ae) {
							boolean loop = true;
							boolean on = true;
							double balance = 0;

							if (acc instanceof CustomerCurrentAccount) {
								int count = 3;
								int checkPin = ((CustomerCurrentAccount) acc).getAtm().getPin();
								loop = true;

								while (loop) {
									if (count == 0) {
										JOptionPane.showMessageDialog(f,
												"Pin entered incorrectly 3 times. ATM card locked.", "Pin",
												JOptionPane.INFORMATION_MESSAGE);
										((CustomerCurrentAccount) acc).getAtm().setValid(false);
										customer();
										loop = false;
										on = false;
									}

									String Pin = JOptionPane.showInputDialog(f, "Enter 4 digit PIN;");
									int i = Integer.parseInt(Pin);

									if (on) {
										if (checkPin == i) {
											loop = false;
											JOptionPane.showMessageDialog(f, "Pin entry successful", "Pin",
													JOptionPane.INFORMATION_MESSAGE);

										} else {
											count--;
											JOptionPane.showMessageDialog(f,
													"Incorrect pin. " + count + " attempts remaining.", "Pin",
													JOptionPane.INFORMATION_MESSAGE);
										}

									}
								}

							}
							if (on == true) {
								String balanceTest = JOptionPane.showInputDialog(f, "Enter amount you wish to lodge:");
								if (isStringNumeric(balanceTest)) {

									balance = Double.parseDouble(balanceTest);
									loop = false;

								} else {
									JOptionPane.showMessageDialog(f, "You must enter a numerical value!", "Oops!",
											JOptionPane.INFORMATION_MESSAGE);
								}

								String euro = "\u20ac";
								acc.setBalance(acc.getBalance() + balance);
								Date date = new Date();
								String date2 = date.toString();
								String type = "Lodgement";
								double amount = balance;

								AccountTransaction transaction = new AccountTransaction(date2, type, amount);
								acc.getTransactionList().add(transaction);

								JOptionPane.showMessageDialog(f, balance + euro + " added do you account!", "Lodgement",
										JOptionPane.INFORMATION_MESSAGE);
								JOptionPane.showMessageDialog(f, "New balance = " + acc.getBalance() + euro,
										"Lodgement", JOptionPane.INFORMATION_MESSAGE);
							}

						}
					});

					withdrawBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent ae) {
							boolean loop = true;
							boolean on = true;
							double withdraw = 0;

							if (acc instanceof CustomerCurrentAccount) {
								int count = 3;
								int checkPin = ((CustomerCurrentAccount) acc).getAtm().getPin();
								loop = true;

								while (loop) {
									if (count == 0) {
										JOptionPane.showMessageDialog(f,
												"Pin entered incorrectly 3 times. ATM card locked.", "Pin",
												JOptionPane.INFORMATION_MESSAGE);
										((CustomerCurrentAccount) acc).getAtm().setValid(false);
										customer();
										loop = false;
										on = false;
									}

									String Pin = JOptionPane.showInputDialog(f, "Enter 4 digit PIN;");
									int i = Integer.parseInt(Pin);

									if (on) {
										if (checkPin == i) {
											loop = false;
											JOptionPane.showMessageDialog(f, "Pin entry successful", "Pin",
													JOptionPane.INFORMATION_MESSAGE);

										} else {
											count--;
											JOptionPane.showMessageDialog(f,
													"Incorrect pin. " + count + " attempts remaining.", "Pin",
													JOptionPane.INFORMATION_MESSAGE);

										}

									}
								}

							}
							if (on == true) {
								String balanceTest = JOptionPane.showInputDialog(f,
										"Enter amount you wish to withdraw (max 500):");
								if (isStringNumeric(balanceTest)) {

									withdraw = Double.parseDouble(balanceTest);
									loop = false;

								} else {
									JOptionPane.showMessageDialog(f, "You must enter a numerical value!", "Oops!",
											JOptionPane.INFORMATION_MESSAGE);
								}
								if (withdraw > 500) {
									JOptionPane.showMessageDialog(f, "500 is the maximum you can withdraw at a time.",
											"Oops!", JOptionPane.INFORMATION_MESSAGE);
									withdraw = 0;
								}
								if (withdraw > acc.getBalance()) {
									JOptionPane.showMessageDialog(f, "Insufficient funds.", "Oops!",
											JOptionPane.INFORMATION_MESSAGE);
									withdraw = 0;
								}

								String euro = "\u20ac";
								acc.setBalance(acc.getBalance() - withdraw);

								Date date = new Date();
								String date2 = date.toString();

								String type = "Withdraw";
								double amount = withdraw;

								AccountTransaction transaction = new AccountTransaction(date2, type, amount);
								acc.getTransactionList().add(transaction);

								JOptionPane.showMessageDialog(f, withdraw + euro + " withdrawn.", "Withdraw",
										JOptionPane.INFORMATION_MESSAGE);
								JOptionPane.showMessageDialog(f, "New balance = " + acc.getBalance() + euro, "Withdraw",
										JOptionPane.INFORMATION_MESSAGE);
							}

						}
					});

					returnBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent ae) {
							f.dispose();
							menuStart();
						}
					});
				}
			});
		}
	}

	public static boolean isStringNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
