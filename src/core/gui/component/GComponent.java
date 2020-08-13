package core.gui.component;

import java.awt.Point;
import java.awt.Shape;
import java.io.Serializable;

import core.Meta;
import core.gui.Display;
import core.gui.design.Sample;
import core.gui.font.Font;

public abstract class GComponent implements Serializable
{
	private static final long serialVersionUID = Meta.serialVersionUID;

	// Determines the type of the GComponent, e.g. image, path or default.
	// This will determine the render method later.
	private String type;
	
	// Determine whether the component should be enabled or not.
	// If it's disabled, it is not just invisible but also you cannot interact with it anymore.
	private boolean enabled = true;

	protected volatile String value = "";

	private volatile String bufferedValue = null;

	// The main reference to all major functions of this whole program.
	private Display display;
	
	private GStyle style;
	
	private GLogic logic;

	public GComponent(String type, Point location, Shape shape, int length, String val, Font font, boolean visible)
	{
		style = new GStyle();
		
		logic = new GLogic();
		
		setType(type);
		getStyle().setLocation(location);

		// When created apply the default design first.
		this.getStyle().setDesign(Sample.classic);
 
		getStyle().setPrimaryColor(getStyle().getDesign().getBackgroundColor());
		getStyle().setShape(shape);

		getStyle().setFont(font);

		getStyle().setVisible(visible);

		// Set all important attributes below:
		getStyle().setLength(length);
		setValue(val);
	}

	// Will write add a new char in the variable 'value' of type String.
	// It will save the value before in the buffer.
	public void write(char key)
	{
		boolean noOverflow = (getValue().length() + 1) <= getStyle().getLength();

		if(noOverflow)
		{
			setValue(getValue() + key);
		}
	}

	// Will do the exact opposite of the write(char key) function.
	// It will delete the last char in the variable 'value' of type String.
	// It will save the value before in the buffer.
	public void eraseLastChar()
	{
		// Checking whether deleting one more char is still possible due to the length
		// of 'value'.
		if(getValue().length() > 0)
		{
			setBufferedValue(getBufferedValue());

			char[] charValues = getValue().toCharArray();

			setValue(getValue().valueOf(charValues, 0, charValues.length - 1));
		}
	}
	
	// Tells you whether the cursor of 'value' is at the beginning,
	// meaning 'value' is empty.
	public boolean isCursorAtBeginning()
	{
		return getValue().length() == 0;
	}
	
	// Tells you whether the cursor of 'value' is at the end,
	// meaning 'value' is full.
	public boolean isCursorAtEnd()
	{
		return (getValue().length() + 1) > getStyle().getLength();
	}

	public synchronized void revert()
	{
		setValue(getBufferedValue());
	}

	public synchronized String getValue()
	{
		return value;
	}

	// The implementation depends on the type,
	// e.g. a text-field is treated differently than an image.
	public abstract void setValue(String val);

	public synchronized void setBufferedValue(String value)
	{
		this.bufferedValue = value;
	}

	public synchronized String getBufferedValue()
	{
		return bufferedValue;
	}

	public abstract void onClick();

	public abstract void onHover();

	public String getType()
	{
		return type;
	}

	private void setType(String type)
	{
		this.type = type;
	}

	public void print()
	{
		System.out.println();
		System.out.println(this);
	}

	@Override
	public String toString()
	{
		return getClass().hashCode() + " (class: " + this.getClass().getSimpleName() + ", type: \"" + getType()
				+ "\"):\ndesign = " + getStyle().getDesign().getClass().getSimpleName() + "\nshape = " + getStyle().getShape() + "\nlength = "
				+ getStyle().getLength() + "\nvalue = \"" + value + "\"\nfontSize = " + getStyle().getFont().getFontSize() + "\nvisible = " + getStyle().isVisible();
	}

	public Display getDisplay()
	{
		return display;
	}

	public void setDisplay(Display display)
	{
		this.display = display;
	}
	
	
	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public GStyle getStyle()
	{
		return style;
	}
	
	public void setStyle(GStyle style)
	{
		this.style = style;
	}
	
	public GLogic getLogic()
	{
		return logic;
	}
	
	public void setLogic(GLogic logic)
	{
		this.logic = logic;
	}
	
	// Updates the shape if possible,
	// meaning if a design available already.
	// Otherwise the component needs to be updated internally with one.
	protected void updateShape()
	{
		if(getStyle().getDesign() != null)
		{
			getStyle().getDesign().updateDefaultShape(this);
		}
	}
}