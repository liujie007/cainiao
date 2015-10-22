package com.hncainiao.fubao.utils;

import java.util.ArrayList;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;
import android.widget.LinearLayout;

public class AChartEngineUtil {
 
	/**
	 * 获取坐标序列
	 * 
	 * @param size
	 *            序列点数 26 * @param values y轴值 27 * @return 坐标x轴序列 或 坐标y轴序列 28
	 */
	public static List<double[]> getlist(int size, List<String> values) {
		List<double[]> xy = new ArrayList<double[]>();

		double[] list = new double[size];
		for (int i = 0; i < size; i++) {
			list[i] = (values.isEmpty()) ? i : Double.valueOf(values.get(i));
		}

		xy.add(list);

		return xy;
	}

	/**
	 * 46 * 构建XYMultipleSeriesRenderer. 47 * 48 * @param colors 每个序列的颜色 49 * @param
	 * styles 每个序列点的类型(可设置三角,圆点,菱形,方块等多种) 50 * ( PointStyle.CIRCLE,
	 * PointStyle.DIAMOND,PointStyle.TRIANGLE, PointStyle.SQUARE ) 51 * @return
	 * XYMultipleSeriesRenderer 52
	 */
	public static XYMultipleSeriesRenderer buildRenderer(int[] colors,
			PointStyle[] styles) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		// 控制横纵轴的属性字大小
		renderer.setAxisTitleTextSize(15);
		// 控制横纵轴的值大小
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setPointSize(5f);
		renderer.setMargins(new int[] { 20, 30, 15, 0 });
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			r.setPointStyle(styles[i]);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}

	/**
	 * 77 * 设置renderer的一些坐标轴属性. 78 * 79 * @param renderer 要设置的renderer 80 * @param
	 * title 图表标题 81 * @param xTitle X轴标题 82 * @param yTitle Y轴标题 83 * @param
	 * xMin X轴最小值 84 * @param xMax X轴最大值 85 * @param yMin Y轴最小值 86 * @param yMax
	 * Y轴最大值 87 * @param axesColor X轴颜色 88 * @param labelsColor Y轴颜色 89
	 */
	public static void setChartSettings(XYMultipleSeriesRenderer renderer,
			String title, String xTitle, String yTitle, double xMin,
			double xMax, double yMin, double yMax, int axesColor,
			int labelsColor) {
		renderer.setChartTitle(title);
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
	}

	/**
	 * 107 *
	 * 构建和时间有关的XYMultipleSeriesDataset,这个方法与buildDataset在参数上区别是需要List<Date[
	 * ]>作参数. 108 * 109 * @param titles 序列图例 110 * @param xValues X轴值 111 * @param
	 * yValues Y轴值 112 * @return XYMultipleSeriesDataset 113
	 */
	public static XYMultipleSeriesDataset buildDataset(String[] titles,
			List<double[]> xValues, List<double[]> yValues) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			XYSeries series = new XYSeries(titles[i]);
			double[] xV = xValues.get(i);
			double[] yV = yValues.get(i);
			int seriesLength = xV.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(xV[k], yV[k]);
			}
			dataset.addSeries(series);
		}
		return dataset;
	}

	/**
	 * 137 * 绘制图表 138 * @param context 当前环境 139 * @param layout 承载图标的容器 140 * @param
	 * titles 折线名称 141 * @param colors 折线颜色 142 * @param data y轴坐标数据 143 * @param
	 * xname x轴名字 144 * @param yname y轴名字 145
	 */
	public static void setchart(Context context, LinearLayout layout,
			String[] titles, int[] colors, List<String> data, String xname,
			String yname) {

		// 获取x轴坐标
		List<double[]> x = getlist(data.size(), new ArrayList<String>());
		// 获取y轴坐标
		List<double[]> values = getlist(data.size(), data);

		PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE };
		// 构建XYMultipleSeriesRenderer
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);

		setChartSettings(renderer, "", xname, yname, 0.5, 12.5, 0, 30,
				Color.LTGRAY, Color.LTGRAY);

		int length = renderer.getSeriesRendererCount();// 获取点数量
		for (int i = 0; i < length; i++) {
			((XYSeriesRenderer) renderer.getSeriesRendererAt(i))
					.setFillPoints(true);// 设置图上的点为实心
		}
		renderer.setXLabels(12);// 设置x轴显示12个点,根据setChartSettings的最大值和最小值自动计算点的间隔
		renderer.setYLabels(10);// 设置y轴显示10个点,根据setChartSettings的最大值和最小值自动计算点的间隔
		renderer.setShowGrid(true);// 是否显示网格
		renderer.setXLabelsAlign(Align.RIGHT);// 刻度线与刻度标注之间的相对位置关系
		renderer.setYLabelsAlign(Align.CENTER);// 刻度线与刻度标注之间的相对位置关系
		renderer.setZoomButtonsVisible(true);// 是否显示放大缩小按钮
		renderer.setPanLimits(new double[] { -10, 300, -10, 300 }); // 设置拖动时X轴Y轴允许的最大值最小值.
		renderer.setZoomLimits(new double[] { -10, 300, -10, 300 });// 设置放大缩小时X轴Y轴允许的最大最小值.

		// 构建view
		View v = ChartFactory.getLineChartView(context,
				buildDataset(titles, x, values), renderer);

		layout.addView(v);
	}

}
