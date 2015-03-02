import java.awt.event.{MouseEvent, MouseAdapter}
import java.awt._
import javax.swing.table.{DefaultTableCellRenderer, TableModel}
import javax.swing._

/**
 * Created by masaakif on 2015/02/27.
 */
object JTableSample extends App {
	val main = new JTableSampleGUI
}

class OKNGRenderer extends DefaultTableCellRenderer {
	override def getTableCellRendererComponent(jt:JTable, v:Object
	                                           , isSelected:Boolean, hasFocus:Boolean
	                                           , row:Int, col:Int) = {
		super.getTableCellRendererComponent(jt,v,isSelected,hasFocus,row,col)
		v.asInstanceOf[String].take(2) match {
			//case "OK" => setBackground(Color.GREEN)
			case "NG" => setBackground(Color.RED)
			case _ => setBackground(jt.getBackground)
		}

		this
	}
}

class JTableSampleGUI extends JFrame {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

	// Create table
	val columns = Array[Object]("File name", "Result")
	val data = Array(Array[Object]("File 1", "OK"), Array[Object]("File 2","NG"))
	val table = new JTable(data, columns) {
		setPreferredScrollableViewportSize(new Dimension(300,200))
		setFillsViewportHeight(true)
		setEnabled(false)

		//setDefaultRenderer(java.lang.Class.forName("java.lang.Object"), new OKNGRenderer)
		addMouseListener(new MouseAdapter() {
			override def mouseClicked(e: MouseEvent): Unit = {
				try {
					val t = e.getSource.asInstanceOf[JTable]
					val row = t.rowAtPoint(e.getPoint);
					val col = t.columnAtPoint(e.getPoint)
					ta.setText("")
					ta.append(s"$row : $col\n")
					ta.append(t.getValueAt(row, col).asInstanceOf[String])
				} catch {
					case e:Exception => println(e)
				}
			}
		})
	}

	// Set JSplitPanel
	val sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT)
	sp.setDividerLocation(400)
	sp.setEnabled(false)


	def newBtn(l:String):JPanel =
		new JPanel{add(new Button(l){setPreferredSize(new Dimension(80,20))})}

	val lp = new JPanel(new BorderLayout){
		val p = new JPanel(new GridLayout(2,2)) {
			add(newBtn("1"))
			add(newBtn("2"))
		}
		add(p, BorderLayout.NORTH)
		add(new JScrollPane(table), BorderLayout.CENTER)
	}
	val ta = new JTextArea{setPreferredSize(new Dimension(400,200))}
	val rp = new JScrollPane(ta)

	sp.setLeftComponent(lp)
	sp.setRightComponent(rp)
	// Show window
	add(sp)
	this.pack
	this.setVisible(true)
}

