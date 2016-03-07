package com.cncyj.mostbrain.game.saolei;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;

import com.cncyj.mostbrain.R;

@SuppressLint("ClickableViewAccessibility")
public class CellImageView extends ImageView {
	private int row;
	private int column;

	public CellImageView(Context context) {
		super(context);
	}

	@SuppressWarnings("static-access")
	public CellImageView(Context context, int row, int column) {
		super(context);
		this.setRow(row);
		this.setColumn(column);
		this.setBackgroundColor(context.getResources().getColor(R.color.BLUE));
		this.setPadding(Utils.dip2px(context, (float) 0.5),
				Utils.dip2px(context, (float) 0.5),
				Utils.dip2px(context, (float) 0.5),
				Utils.dip2px(context, (float) 0.5));
		this.setImageDrawable(context.getResources().getDrawable(
				R.drawable.bg_green));
		this.setScaleType(getScaleType().FIT_XY);
		this.setMinimumWidth(Utils.dip2px(context, 30));
		this.setMinimumHeight(Utils.dip2px(context, 30));
		this.setMaxHeight(Utils.dip2px(context, 30));
		this.setMaxWidth(Utils.dip2px(context, 30));
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setImageDrawable(int drawableId) {
		super.setImageDrawable(MineSweeperActivity.drawables[drawableId]);
	}
}
