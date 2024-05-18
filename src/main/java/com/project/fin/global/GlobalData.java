package com.project.fin.global;

import java.util.ArrayList;
import java.util.List;

import com.project.fin.model.Product;

public class GlobalData {
	public static List<Product> cart;
	static {
		cart =new ArrayList<Product>();
	}
}
