package core.gui.special;

import java.awt.Color;
import java.awt.Point;

import core.Meta;
import core.gui.component.GComponent;
import core.gui.font.Font;

public abstract class GTextfield extends GComponent
{
	private static final long serialVersionUID = Meta.serialVersionUID;
	
	private boolean active = false;

	private Color clicked;

	public GTextfield(Point location, String title, int maxInput, Font font, boolean visible)
	{
		super("textfield", location, null, -1, title, font, visible);
		
		if (maxInput > 0)
			getStyle().setLength(maxInput);
		else
			throw new IllegalArgumentException("Maximum length must be 1 or longer!");

		if (title.length() <= getStyle().getLength())
			setValue(title);
		else
			throw new IllegalArgumentException("Title length is bigger than the specified maximum length!");
		
		// This method is always called after the base values have been set, e.g. font size.
		updateShape();
	}

	public void onClick(){}
	
	public void setActive()
	{
		if(getStyle().getPrimaryColor() == null)
		{
			return;
		}
		
		getStyle().setBufferedColor(getStyle().getPrimaryColor());
		
		getStyle().setPrimaryColor(getColorClicked());
		
		active = true;
	}
	
	public void setInactive()
	{
		if(getStyle().getBufferedColor() == null)
		{
			return;
		}
		
		getStyle().setPrimaryColor(getStyle().getBufferedColor());
		
		getStyle().setBufferedColor(null);
		
		active = false;
	}

	public boolean isActive()
	{
		return active;
	}

	public abstract void onHover();

	public Color getColorClicked()
	{
		return clicked;
	}

	public void setClicked(Color clicked)
	{
		this.clicked = clicked;
	}
	
	@Override
	public void setValue(String title)
	{
		if(title == null)
		{
			return;
		}

		this.value = title;

		updateShape();
	}
}