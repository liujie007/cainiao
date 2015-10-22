/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hncainiao.fubao.ui.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

/**
 * A simple dialog containing an {@link android.widget.DatePicker}.
 *
 * <p>See the <a href="{@docRoot}resources/tutorials/views/hello-datepicker.html">Date Picker
 * tutorial</a>.</p>
 */

public class DiyDatePickerDialog extends DatePickerDialog  {

	private int year,mome,day;
	public DiyDatePickerDialog(Context context, int theme,
			OnDateSetListener callBack, int year, int monthOfYear,
			int dayOfMonth) {
		super(context, theme, callBack, year, monthOfYear, dayOfMonth);
		// TODO Auto-generated constructor stub
		this.year=year;
		this.mome=monthOfYear;
		this.day=dayOfMonth;
	}
	@Override
	public void onDateChanged(DatePicker view, int year, int month, int day) {
		// TODO Auto-generated method stub
		if (year>this.year) {
			super.onDateChanged(view, year, month, day);
		}
	
	}
	@Override
	public void updateDate(int year, int monthOfYear, int dayOfMonth) {
		// TODO Auto-generated method stub
		if (year>this.year) {
			
		}
		super.updateDate(year, monthOfYear, dayOfMonth);
		
	}


}
