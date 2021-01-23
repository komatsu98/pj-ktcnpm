package ga;

import java.util.*;

import manufacturer.Manufacturer;
import retailer.Retailer;

import java.util.ArrayList;

import config.Config;

public class GA {
	public ArrayList<Manufacturer> manufacturer_list;
	public ArrayList<Double> profit_list;
	int n;
	double p_selection = 0.8;
	double p_crossover = 0.2;
	double p_mutation = 0.3;
	int H = 40000;
	public int best_id = 0;
	Config config = new Config();
	
	public GA(int n) {
		this.n = n;
		for(int i = 0; i < n; i ++) {
			this.profit_list.add(0.0);
		}
	}
	
	public double calculator_deman(int[] a, int A, double[] e_a, double e_A, double[] K) {
		double D = 0;
		for(int i = 0; i < a.length; i ++) {
			D += K[i] * Math.pow(a[i], e_a[i]) * Math.pow(A, e_A);
		}
		return D;
	}
	
	public double calculator_deman_1(int t) {
		double D = 0;
		for(int i = 0; i < config.e_a.length; i ++) {
			double a_i = this.manufacturer_list.get(t).retailer_list.get(i).a;
			double e_ai = this.manufacturer_list.get(t).retailer_list.get(i).e_a;
			double A = this.manufacturer_list.get(t).A;
			double e_A = this.manufacturer_list.get(t).e_A;
		    D += config.K[i] * Math.pow(a_i, e_ai) * Math.pow(A, e_A);
		}
		return D;
	}
	
	public int rand(int min, int max) {
		return (int)(Math.random() * (max - min)) + min;
	}
	
	public void create() {
		for(int i = 0; i < this.n; i ++) {
            int A = this.H;
            int[] a = {this.H, this.H, this.H, this.H, this.H};
            int[] n_j = new int[config.M.length];
            while (this.calculator_deman(a, A, config.e_a, config.e_A, config.K) > config.P) {
                A = rand(1, this.H);
                for(int j = 0; j < a.length; j ++) {
                    a[j] = rand(1, this.H);
                }
            }
            for(int j = 0; j < config.M.length; j ++) {
            	n_j[j] = rand(2, 20);
            }
            int x;        
            if (this.calculator_deman(a, A, config.e_a, config.e_A, config.K) < config.P) {
                x = 1;
            }
            else {
                x = 0;
            }
            
            Manufacturer manufacturer = new Manufacturer(config.cm, config.cr, config.H_p, config.H_r, config.M, config.P, config.S_p, config.S_r, A, config.e_A, 0, n_j, x) ;
            for(int j = 0; j < a.length; j ++) {
            	Retailer retainer = new Retailer(config.e_a[j], config.K[j], config.phi[j], config.uc[j], config.cp[j], config.H_b[j], config.L_b[j], config.S_b[j], config.p[j], a[j], 0, 0);
                retainer.calculator_b();
                manufacturer.retailer_list.add(retainer);
            }
            manufacturer.caculator_C();
            this.manufacturer_list.add(manufacturer);
		}
	}
	
	public void evaluation() {
		for(int i = 0; i < this.n; i ++) {
			double profit = 0;
		    profit += this.manufacturer_list.get(i).calculator_manufacturer_profit();
		    for(int j = 0; j < config.M.length; j ++) {
		    	double e_A = this.manufacturer_list.get(i).e_A;
		        double A = this.manufacturer_list.get(i).A;
		        profit += this.manufacturer_list.get(i).retailer_list.get(j).calculator_retailer_profit(A, e_A);
		    }
		    this.profit_list.set(i,profit);
		}
	}
	
	public void selection() {
		ArrayList<Double> temp = new ArrayList<>(profit_list);
		Collections.sort(temp);
		double threshold = temp.get((int)(1-this.p_selection) * this.n);
		for(int i = 0; i < this.n; i ++) {
			if (this.profit_list.get(i) < threshold) {
                int k = rand(0, this.n);
                this.manufacturer_list.set(i, this.manufacturer_list.get(k));
			}
		}
	}
	
	public void crossover() {
		for(int i = 0; i < (int) this.n*this.p_crossover; i ++) {
			int father = rand(0, this.n);
		    int mother = rand(0, this.n);

		    // crossover A
		    if (rand(0, 2) == 1){
		    	this.manufacturer_list.get(father).A = this.manufacturer_list.get(mother).A;
		    	this.manufacturer_list.get(mother).A = this.manufacturer_list.get(father).A;
		        if (this.calculator_deman_1(father) > config.P || this.calculator_deman_1(mother) > config.P) {
		        	this.manufacturer_list.get(father).A = this.manufacturer_list.get(mother).A;
		        	this.manufacturer_list.get(mother).A = this.manufacturer_list.get(father).A;
		        }
		    }
            // crossover a
		    for(int j=0; j < config.e_a.length; j++) {
		    	if (rand(0,2) == 1) {
                    this.manufacturer_list.get(father).retailer_list.get(j).a = this.manufacturer_list.get(mother).retailer_list.get(j).a;
                    this.manufacturer_list.get(mother).retailer_list.get(j).a = this.manufacturer_list.get(father).retailer_list.get(j).a;
                    if (this.calculator_deman_1(father) > config.P || this.calculator_deman_1(mother) > config.P) {
                    	this.manufacturer_list.get(father).retailer_list.get(j).a = this.manufacturer_list.get(mother).retailer_list.get(j).a;
                    	this.manufacturer_list.get(mother).retailer_list.get(j).a = this.manufacturer_list.get(father).retailer_list.get(j).a;            
                    }
		    	}
		    }
            // crossover n_j
            for(int j=0; j < config.H_r.length; j++) {
                if (rand(0,2) == 1) {
                    int temp = this.manufacturer_list.get(father).n[j];
                    this.manufacturer_list.get(father).n[j] = this.manufacturer_list.get(mother).n[j];
                    this.manufacturer_list.get(mother).n[j] = temp;
                }
            }
            this.manufacturer_list.get(father).caculator_C();
            this.manufacturer_list.get(mother).caculator_C();
		}
	}
	
	public void mutation() {
		for(int i = 0; i < (int) this.n*this.p_mutation; i ++) {
			int index = rand(0, this.n);
		    // mutation A
		    while (this.calculator_deman_1(index) > config.P) {
		    	this.manufacturer_list.get(index).A = rand(1, this.H);
		    }
		    
		    // mutation a
            while (this.calculator_deman_1(index) > config.P) {
                for (int j=0; j < config.e_a.length; j++) {
                    if (rand(0, 2) == 1) {
                        this.manufacturer_list.get(index).retailer_list.get(j).a = rand(1, this.H);
                    }
                }
            }
            
            // mutation n
            for (int j=0; j < config.M.length; j++) {
                if (rand(0, 2) == 1) {
                    this.manufacturer_list.get(index).n[j] = rand(2, 20);
                }
            }
            this.manufacturer_list.get(index).caculator_C();
		}
	}
	
	public void show() {
		ArrayList<Double> temp = new ArrayList<>(profit_list);
		Collections.sort(temp);
        double best = temp.get(-1);
        System.out.println(best);
		for(int i = 0; i < this.n; i ++) {
			if (this.profit_list.get(i) == best) {
				this.best_id = i;
			      break;
			}
		}
	}
}
