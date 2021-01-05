import java.util.Collections;
import java.util.Vector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MainPage extends Application{
	Statement stmt = null;
	Statement stmtProduct= null;
	Statement stmtPrice = null;
	Statement stmtAlpProduct = null;
	Statement stmtAlpPrice = null;
	Statement stmtOpen = null;
	Statement stmtClose = null;
	Statement stmtAlpOpen =null;
	Statement stmtAlpClose=null;
	Statement stmtX=null;
	int indexCount=0;
	Login ad= new Login();
	
	
	@Override
	public void start(Stage menuGui) throws Exception { 
	    Connection connection = null;
	    Path path = new Path();
		String databaseName = path.databaseName;
		String url = path.url+databaseName;
		String username = path.username;
		String password = path.password;
		Vector<String> RestName = new Vector<String>();
		Vector<String> location = new Vector<String>();
		Vector<String> alp= new Vector<String>();
		Vector<Integer> xCoord = new Vector<Integer>();
		Vector<Integer> yCoord = new Vector<Integer>();
		Vector<Menu> m = new Vector<Menu>();
		Vector<String>  openalp = new Vector<String>();
		Vector<String>	closealp = new Vector<String>();

    
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Can't find Driver");
				e.printStackTrace();
			}
			   
			   try {
				connection = DriverManager.getConnection(url, username, password);
				if(!connection.isClosed()) {
					System.out.println("DB Connected");
				}
			} catch (SQLException e) {
				System.out.println("Connection failed");
				e.printStackTrace();
			}


			try {
				stmt = connection.createStatement();
				stmtProduct = connection.createStatement();
				stmtPrice = connection.createStatement();
				stmtAlpPrice = connection.createStatement();
				stmtAlpProduct = connection.createStatement();
				stmtOpen = connection.createStatement();
				stmtClose = connection.createStatement();
				stmtAlpOpen = connection.createStatement();
				stmtAlpClose = connection.createStatement();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			   String sql;
			   String x;
			   String y;
			   String open;
			   String close;
			   sql = "select RName from Restaurant";
			   x="select XCoord from Restaurant";
			   y="select YCoord from Restaurant";
			   open="select Open from Restaurant";
			   close="select Close from Restaurant";
			   ResultSet ret = null;
			   ResultSet xrs = null;
			   ResultSet yrs = null;	
			   ResultSet openrs = null;
			   ResultSet closers = null;
			   
			try {
				ret = stmt.executeQuery(sql);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				while(ret.next()) {
					   String name = null;
					try {
						name = ret.getString("RName");
						RestName.addElement(name);
			//			normalOrder.addElement(name);
			//			location.addElement(name);
						alp.addElement(name);
						Menu q = new Menu();
						q.Rname=name;
						m.addElement(q);
					

					} catch (SQLException e) {
						e.printStackTrace();
					}
				   }
				
			} catch (SQLException e) {
				e.printStackTrace();
			}//MainGui
			
			

	   	int count = RestName.size();
	   	
	   	try {
			xrs = stmt.executeQuery(x);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while(xrs.next()) {
				int xaddress;
				xaddress=xrs.getInt("XCoord");
				xCoord.addElement(xaddress);			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			yrs = stmt.executeQuery(y);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while(yrs.next()) {
				int yaddress;
				yaddress=yrs.getInt("YCoord");
				yCoord.addElement(yaddress);			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	
	   	try {
			openrs = stmtAlpOpen.executeQuery(open);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while(openrs.next()) {
				String ors;
				ors=openrs.getString("Open");
				openalp.addElement(ors);			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}//opentimeforsort
		
		
		
	   	try {
			closers = stmt.executeQuery(close);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while(closers.next()) {
				String crs;
				crs=closers.getString("Close");
				closealp.addElement(crs);			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}//closetimeforsort
		
		
		
		for(int i=0;i<count;i++) {
			m.elementAt(i).x=xCoord.elementAt(i);
			m.elementAt(i).y=yCoord.elementAt(i);
			m.elementAt(i).opentime.setText(openalp.elementAt(i));			
			m.elementAt(i).closedate.setText(closealp.elementAt(i));
		}
		
	   	   	
	   	Menu b[] = new Menu[count];	    
	    for(int k=0;k<count;k++) {
	    	Menu c= new Menu();	    
	    	b[k]=c;    	    	
	    }

	//	Group group = new Group();
		BorderPane border = new BorderPane();
		HBox hbox = new HBox();
		border.setTop(hbox);
		VBox vbox = new VBox();
		border.setLeft(vbox);
		GridPane grid = new GridPane();
		border.setCenter(grid);
		Button admin=new Button("Admin");
		
		admin.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					ad.start(count,RestName);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		});
		
//adminbutton
		
		Scene scene = new Scene(border,700,600);
		menuGui.setTitle("Menu");
		menuGui.setScene(scene);
		menuGui.show();
		
		
		Label label = new Label("Your Location:");
		label.setFont(new Font("Arial",17));
		label.setStyle("-fx-font-weight: bold");
		TextField textField = new TextField ();
		Button sortOne = new Button("Sort by location");

		sortOne.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				RestName.clear();
				grid.getChildren().clear();
				
				Text Default = new Text("Restaurant List");
			    Default.setFont(Font.font("Arial", FontWeight.BOLD, 30));
			    grid.add(Default, 0, 0);
			    
				String loc = textField.getText();
				String xy[]=loc.split(",");
				int lx =Integer.parseInt(xy[0]);
				int ly =Integer.parseInt(xy[1]);
				double dis[] = new double[count];
				for(int i=0;i<count;i++) {
					double d=0;
					d=Math.sqrt(Math.pow((lx-xCoord.elementAt(i)),2)+Math.pow(ly-yCoord.elementAt(i), 2));
					dis[i]=d;
					m.elementAt(i).distance=d;
				}
				
//sort distance				
				for(int j=count-1;j>0;j--) {
					for(int k=1;k<=j;k++) {
						if(dis[k-1]>dis[k]) {
							double temp = dis[k-1];
							dis[k-1] = dis[k];
							dis[k] = temp;
						}
					}
				}
				
				
				for(int i=0;i<count;i++) {
					int j=i;

				    Menu d = new Menu();
				    boolean finish =false;
				    
				    for(int p=0;p<count;p++) {		
				    		if((dis[j]==m.elementAt(p).distance&&(!m.elementAt(p).mark)&&!finish)) {	
				    			location.addElement(m.elementAt(p).Rname);
				    			d.x=m.elementAt(p).x;
				    			d.y=m.elementAt(p).y;
				    			d.opentime.setText(m.elementAt(p).opentime.getText());
				    			d.closedate.setText(m.elementAt(p).closedate.getText());
				    			m.elementAt(p).mark=true;
				    			finish=true;
				    		}
				    }
				    
				    b[j]=d;		

					RestName.addElement(location.elementAt(j));	
					Hyperlink category= new Hyperlink((String) RestName.elementAt(j));
				    category.setFont(Font.font("Arial", FontWeight.BOLD, 18));
				    Text ax = new Text(String.valueOf(d.x));
				    Text ay = new Text(String.valueOf(d.y));
					ax.setFont(Font.font("Arial", FontWeight.MEDIUM, 17));
					ay.setFont(Font.font("Arial", FontWeight.MEDIUM, 17));
					grid.add(category, 0, j+1);
					grid.add(ax, 1, j+1);
					grid.add(ay, 2, j+1);
					
					
					category.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							if(!b[j].newStage.isShowing()) {			
								b[j].v.getChildren().clear();
								b[j].menugrid.getChildren().clear();
								b[j].start((String) RestName.elementAt(j));
																	
								String productSql;
			    				productSql ="select Product from "+RestName.elementAt(j)+",Restaurant where Restaurant.idRestaurant="+RestName.elementAt(j)+".Rid";				    				
			    				ResultSet productSet =null;
			    		//load productset		
								try {
			    					productSet = stmtAlpProduct.executeQuery(productSql);			    					
			    				} catch (SQLException ex) {
			    					ex.printStackTrace();
			    				}
								
								try {
			    					int c=0;
			    					int r=2;
			    					while(productSet.next()) {
			    						
			    						String product=null;
			    						try {
			    							product = productSet.getString("Product");
			    							Text pt = new Text(product);
			    						    pt.setFont(Font.font("Arial", FontWeight.BOLD, 15));
			    						    
			    						    if(c==2) {
			    						    	b[j].menugrid.add(pt, c, r);
			    						    	c=0;
			    						    	r++;
			    						    }
			    						    else if(c==0) {
			    						    	b[j].menugrid.add(pt, c, r);
			    						    	c=2;
			    						    }

			    						} catch (SQLException ex) {
			    							ex.printStackTrace();
			    						}
			    					   }
			    				} catch (SQLException ex) {
			    					ex.printStackTrace();
			    				}//loadproduct end
			    		
								//Load productPrice	    				
								String priceSql;
			    				priceSql ="select Price from "+RestName.elementAt(j)+",Restaurant where Restaurant.idRestaurant="+RestName.elementAt(j)+".Rid";
			    				ResultSet priceSet =null;
			    				
			    				try {
			    					priceSet = stmtAlpPrice.executeQuery(priceSql);
			    				
			    					
			    				} catch (SQLException ex) {
			    					ex.printStackTrace();
			    				}				    				
			    				
			    				try {
			    					int c=1;
			    					int r=2;
			    					while(priceSet.next()) {
			    						String price =null;
			    						
			    						try {
			    							price = priceSet.getString("Price");
			    					
			    							Text pct = new Text(price);
			    						    pct.setFont(Font.font("Arial", FontWeight.BOLD, 15));	    						    
			    						    
			    						    if(c==3) {
			    						       b[j].menugrid.add(pct, c, r);
			    						       c=1;
				    						   r++;
			    						    }
			    						    
			    						    else if(c==1) {
			    						       b[j].menugrid.add(pct, c, r);
			    						       c=3;
			    						    }				    

			    						} catch (SQLException ex) {
			    							ex.printStackTrace();
			    						}
			    					   }
			    				} catch (SQLException ex) {
			    					ex.printStackTrace();
			    				}
			    				//loadprice end
		
						}//if notshowing

							
						}
					});//loadmenu

			}//for loop
			for(int r =0;r<count;r++) {
				m.elementAt(r).mark=false;
			}
		}
});
		
		
		
		

//sortbyloaction start		
		Button sortTwo = new Button("Sort by A-Z");
		sortTwo.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
					Collections.sort(alp);
					RestName.clear();
					grid.getChildren().clear();
					
					Text Default = new Text("Restaurant List");
				    Default.setFont(Font.font("Arial", FontWeight.BOLD, 30));
				    grid.add(Default, 0, 0);
					for(int i=0;i<count;i++) {
						int j=i;
	
					    Menu d = new Menu();
					    boolean finish =false;
					    for(int p=0;p<count;p++) {
					    	if(alp.elementAt(j).equals(m.elementAt(p).Rname)&&(!m.elementAt(p).mark)&&!finish) {	
					    	  d.x=m.elementAt(p).x;
					    	  d.y=m.elementAt(p).y;
					    	  d.opentime.setText(m.elementAt(p).opentime.getText());
					    	  d.closedate.setText(m.elementAt(p).closedate.getText());
					    	  m.elementAt(p).mark=true;
					    	  finish=true;
					    	}
					    }
					    b[j]=d;		

						RestName.addElement(alp.elementAt(j));	
						Hyperlink category= new Hyperlink((String) RestName.elementAt(j));
					    category.setFont(Font.font("Arial", FontWeight.BOLD, 18));
					    Text ax = new Text(String.valueOf(d.x));
					    Text ay = new Text(String.valueOf(d.y));
						ax.setFont(Font.font("Arial", FontWeight.MEDIUM, 17));
						ay.setFont(Font.font("Arial", FontWeight.MEDIUM, 17));
						grid.add(category, 0, j+1);
						grid.add(ax, 1, j+1);
						grid.add(ay, 2, j+1);
						
						category.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								if(!b[j].newStage.isShowing()) {			
									b[j].v.getChildren().clear();
									b[j].menugrid.getChildren().clear();
									b[j].start((String) RestName.elementAt(j));
																		
									String productSql;
				    				productSql ="select Product from "+RestName.elementAt(j)+",Restaurant where Restaurant.idRestaurant="+RestName.elementAt(j)+".Rid";				    				
				    				ResultSet productSet =null;
				    		//load productset		
									try {
				    					productSet = stmtAlpProduct.executeQuery(productSql);			    					
				    				} catch (SQLException ex) {
				    					ex.printStackTrace();
				    				}
									
									try {
				    					int c=0;
				    					int r=2;
				    					while(productSet.next()) {
				    						
				    						String product=null;
				    						try {
				    							product = productSet.getString("Product");
				    							Text pt = new Text(product);
				    						    pt.setFont(Font.font("Arial", FontWeight.BOLD, 15));
				    						    
				    						    if(c==2) {
				    						    	b[j].menugrid.add(pt, c, r);
				    						    	c=0;
				    						    	r++;
				    						    }
				    						    else if(c==0) {
				    						    	b[j].menugrid.add(pt, c, r);
				    						    	c=2;
				    						    }

				    						} catch (SQLException ex) {
				    							ex.printStackTrace();
				    						}
				    					   }
				    				} catch (SQLException ex) {
				    					ex.printStackTrace();
				    				}//loadproduct end
				    		
									
									
			//Load productPrice	    				
									String priceSql;
				    				priceSql ="select Price from "+RestName.elementAt(j)+",Restaurant where Restaurant.idRestaurant="+RestName.elementAt(j)+".Rid";
				    				ResultSet priceSet =null;
				    				
				    				try {
				    					priceSet = stmtAlpPrice.executeQuery(priceSql);
				    				
				    					
				    				} catch (SQLException ex) {
				    					ex.printStackTrace();
				    				}				    				
				    				
				    				try {
				    					int c=1;
				    					int r=2;
				    					while(priceSet.next()) {
				    						String price =null;
				    						
				    						try {
				    							price = priceSet.getString("Price");
				    					
				    							Text pct = new Text(price);
				    						    pct.setFont(Font.font("Arial", FontWeight.BOLD, 15));	    						    
				    						    
				    						    if(c==3) {
				    						       b[j].menugrid.add(pct, c, r);
				    						       c=1;
					    						   r++;
				    						    }
				    						    
				    						    else if(c==1) {
				    						       b[j].menugrid.add(pct, c, r);
				    						       c=3;
				    						    }				    

				    						} catch (SQLException ex) {
				    							ex.printStackTrace();
				    						}
				    					   }
				    				} catch (SQLException ex) {
				    					ex.printStackTrace();
				    				}
				    				//loadprice end
			
							}//if notshowing

								
							}
						});//loadmenu

				}//for loop
				for(int r =0;r<count;r++) {
					m.elementAt(r).mark=false;
				}
					
					
			}//handler
			
		});
//sortbylocation		
		
		
		hbox.setStyle("-fx-background-color: #336699;");
		vbox.getChildren().addAll(label,textField,sortOne,sortTwo,admin);
	    vbox.setSpacing(30);	    
	    vbox.setPadding(new Insets(30,10,10,10));
		vbox.setStyle("-fx-background-color: #2eb8ea;");
		
		
	    grid.setHgap(30);
	    grid.setVgap(15);
	    grid.setPadding(new Insets(5, 10, 0, 20));    
	    
	    Text Default = new Text("Restaurant List");
	    Default.setFont(Font.font("Arial", FontWeight.BOLD, 30));
	    grid.add(Default, 0, 0);


//loadmainmenu
	    for(int i=0;i<count;i++) {
	    int j=i;
	    
	    String xc = xCoord.elementAt(j).toString();
	    String yc = yCoord.elementAt(j).toString();
	    Hyperlink category= new Hyperlink((String) (RestName.elementAt(j)));
	    Text txc = new Text(xc);
	    Text tyc = new Text(yc);
	    category.setFont(Font.font("Arial", FontWeight.BOLD, 18));
	    txc.setFont(Font.font("Arial", FontWeight.MEDIUM, 17));
	    tyc.setFont(Font.font("Arial", FontWeight.MEDIUM, 17));
	    grid.add(category, 0, j+1);	
	    grid.add(txc,1,j+1);
	    grid.add(tyc, 2, j+1);

	    category.setOnAction(new EventHandler<ActionEvent>() {
	    	
	    	public void handle(ActionEvent e) {
	    		if(category.getText().equals(RestName.elementAt(j))&&!b[j].newStage.isShowing()) {	   
	    			
					b[j].v.getChildren().clear();
					b[j].menugrid.getChildren().clear();
	    			
	    			try {
	    				String productSql;
	    				productSql ="select Product from "+RestName.elementAt(j)+",Restaurant where Restaurant.idRestaurant="+RestName.elementAt(j)+".Rid";
	    				ResultSet productSet =null;
//Load productName    				
	    				try {
	    					productSet = stmtProduct.executeQuery(productSql);			
	    				} catch (SQLException ex) {
	    					ex.printStackTrace();
	    				}
	    				
	    				
	    				try {
	    					int c=0;
	    					int r=2;
	    				//	System.out.println(b[j].clicked);
	    					while(productSet.next()) {
	    						
	    						String product=null;
	    						try {
	    							product = productSet.getString("Product");
	    							Text pt = new Text(product);
	    						    pt.setFont(Font.font("Arial", FontWeight.BOLD, 15));
	    						    
	    						    if(c==2) {
	    						    	b[j].menugrid.add(pt, c, r);
	    						    	c=0;
	    						    	r++;
	    						    }
	    						    else if(c==0) {
	    						    	b[j].menugrid.add(pt, c, r);
	    						    	c=2;
	    						    }

	    						} catch (SQLException ex) {
	    							ex.printStackTrace();
	    						}
	    					   }
	    				} catch (SQLException ex) {
	    					ex.printStackTrace();
	    				}
			
	//Load Opentime 	    				
	     				String restopen;
	    				restopen ="select Open from "+RestName.elementAt(j)+",Restaurant where Restaurant.idRestaurant="+RestName.elementAt(j)+".Rid";
	    				ResultSet openSet =null;
   				
	    				try {
	    					openSet = stmtOpen.executeQuery(restopen); 					
	    					
	    				} catch (SQLException ex) {
	    					ex.printStackTrace();
	    				}
	    					    				
	    				try {
	    					while(openSet.next()) {
	    						
	    						String open=null;
	    						try {
	    							open = openSet.getString("Open");
	    						    b[j].opentime.setText(open);

	    						} catch (SQLException ex) {
	    							ex.printStackTrace();
	    						}
	    					   }
	    				} catch (SQLException ex) {
	    					ex.printStackTrace();
	    				}
    				
	    		    
	    				
	    				String restclose;
	    				restclose ="select Close from "+RestName.elementAt(j)+",Restaurant where Restaurant.idRestaurant="+RestName.elementAt(j)+".Rid";
	    				ResultSet closeSet =null;
   				
	    				try {
	    					closeSet = stmtClose.executeQuery(restclose); 					
	    					
	    				} catch (SQLException ex) {
	    					ex.printStackTrace();
	    				}
	    					    				
	    				try {
	    					while(closeSet.next()) {
	    						
	    						String close=null;
	    						try {
	    							close = closeSet.getString("Close");
	    						    b[j].closedate.setText(close);

	    						} catch (SQLException ex) {
	    							ex.printStackTrace();
	    						}
	    					   }
	    				} catch (SQLException ex) {
	    					ex.printStackTrace();
	    				}
    				
	    				
	    				
	    				
	    				
//Load productPrice	    				
	    				String priceSql;
	    				priceSql ="select Price from "+RestName.elementAt(j)+",Restaurant where Restaurant.idRestaurant="+RestName.elementAt(j)+".Rid";
	    				ResultSet priceSet =null;
	    				
	    				try {
	    					priceSet = stmtPrice.executeQuery(priceSql);
	    				
	    					
	    				} catch (SQLException ex) {
	    					ex.printStackTrace();
	    				}
	    				
	    				
	    				try {
	    					int c=1;
	    					int r=2;
	    					while(priceSet.next()) {
	    						String price =null;
	    						
	    						try {
	    							price = priceSet.getString("Price");
	    					
	    							Text pct = new Text(price);
	    						    pct.setFont(Font.font("Arial", FontWeight.BOLD, 15));	    						    
	    						    
	    						    if(c==3) {
	    						       b[j].menugrid.add(pct, c, r);
	    						       c=1;
		    						   r++;
	    						    }
	    						    
	    						    else if(c==1) {
	    						       b[j].menugrid.add(pct, c, r);
	    						       c=3;
	    						    }				    

	    						} catch (SQLException ex) {
	    							ex.printStackTrace();
	    						}
	    					   }
	    				} catch (SQLException ex) {
	    					ex.printStackTrace();
	    				}
	    				
						b[j].start((String) RestName.elementAt(j));

	    				
					} catch (Exception e1) {
						e1.printStackTrace();
					}
	    		}
	    		//b[j].clicked=true;
	    	}
    	
	    });//Click link    

	    }//load MainMenu
	
	}
//start	
	
	
	public static void main(String args[]){     		
		      launch(args);     
	   }
}
