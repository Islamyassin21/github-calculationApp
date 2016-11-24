package com.example.islam.tawasoltaskaccountingapplication;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.islam.tawasoltaskaccountingapplication.Model.MyTask;
import com.example.islam.tawasoltaskaccountingapplication.Tap.SlidingTabLayout;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTaps;
    private Locale myLocale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //  preferences = getSharedPreferences("name", MODE_PRIVATE);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        mTaps = (SlidingTabLayout) findViewById(R.id.tab);
        mTaps.setViewPager(mPager);

    }


    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        //  super.onRestart();
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        String[] tabs;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = null;


            MyFragment myFragment = MyFragment.getInstance(position);


            return myFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


    public static class MyFragment extends Fragment {

        private DatabaseHelper db;
        private Button searchButton;
        private EditText search;
        private Spinner spinnerFilter, spinnerSort;
        private ListView listView, last12MonthType, last12MonthPlace;
        private AccountingAdapter acountAdapter;
        private Last12MonthAdapter last12MonthAdapter1;
        private Last12MonthAdapterExpenses last12MonthAdapter2;
        private ArrayList<MyTask> dataList = new ArrayList<>();
        private ArrayList<MyTask> dataList2 = new ArrayList<>();
        private ArrayList<MyTask> dataList3 = new ArrayList<>();
        private LineGraphSeries<DataPoint> seriesOfBalance, seriesOfExpenses;
        private double y, x, w, z;
        private List<Integer> ListAmountOfPlannedofbalance, ListAmountOfIncomeofbalance, ListAmountOfExpensess2ofbalance;
        int totalOfIncome = 0;
        int totalOfExpenses = 0;
        int totalOfPlanned = 0;
        private Spinner year;
        public TextView balance;
        public MyTask myTask;
        public FloatingActionButton fab;
        public View layout = null;
        public SharedPreferences preferences;
        public Toolbar toolbar;
        public NavigationView navigationView;

        public static MyFragment getInstance(int position) {
            MyFragment myFragment = new MyFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            myFragment.setArguments(args);
            return myFragment;
        }

        private static final int TAB_ONE = 0;
        private static final int TAB_TWO = 1;
        private static final int TAB_THREE = 2;
        private static final int TAB_FOUR = 3;
        public static final int FILTER_SERVICES = 1;
        public static final int FILTER_CLOTHES = 2;
        public static final int FILTER_DELIVERY = 3;
        public static final int FILTER_FIXES = 4;
        public static final int FILTER_FOOD = 5;
        public static final int FILTER_INCOME = 6;
        public static final int FILTER_OTHER = 7;
        public static final int SORT_TYPY = 1;
        public static final int SORT_DATE = 2;


        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle context) {

            preferences = getContext().getSharedPreferences("name", MODE_PRIVATE);

            Bundle bundle = getArguments();
            int i = bundle.getInt("position");
            layout = inflater.inflate(R.layout.first_tab, container, false);
            searchButton = (Button) layout.findViewById(R.id.position);
            search = (EditText) layout.findViewById(R.id.EditTextSearch);
            spinnerFilter = (Spinner) layout.findViewById(R.id.spinnerSearch);
            spinnerSort = (Spinner) layout.findViewById(R.id.spinnerSort);
            listView = (ListView) layout.findViewById(R.id.listView);
            fab = (FloatingActionButton) layout.findViewById(R.id.fab);

            switch (i) {
                case TAB_ONE:

                    FirstTab();

                    break;
/******************************************************************************************************************************************/
                case TAB_TWO:
                    layout = inflater.inflate(R.layout.fourth_tap, container, false);

                    SecondTab();

                    break;
/******************************************************************************************************************************************/
                case TAB_THREE:
                    layout = inflater.inflate(R.layout.secod_tab, container, false);

                    TheridTab();

                    break;
/*******************************************************************************************************************************************/
                case TAB_FOUR:
                    layout = inflater.inflate(R.layout.third_tab, container, false);

                    FourithTab();

                    break;
/******************************************************************************************************************************************/
            }

            return layout;
        }

        @Override
        public void onResume() {
            super.onResume();
            try {

                preferences = getContext().getSharedPreferences("name", MODE_PRIVATE);
                Log.v("datadata", preferences.getString("costumerName", "mmm"));
                dataList.clear();
                db = new DatabaseHelper(getActivity());
                ArrayList<MyTask> transactionList = db.getAllTransactionOrderByMonth(preferences.getString("costumerName", ""));
                for (int j = 0; j < transactionList.size(); j++) {

                    String mtype = transactionList.get(j).getType();
                    String mplace = transactionList.get(j).getPlace();
                    String mDescription = transactionList.get(j).getDescreption();
                    String mAmount = transactionList.get(j).getAmount();
                    Integer mDay = transactionList.get(j).getDay();
                    Integer mMonth = transactionList.get(j).getMonth();
                    Integer mYear = transactionList.get(j).getYear();
                    int mid = transactionList.get(j).getItemId();

                    myTask = new MyTask();
                    myTask.setType(mtype);
                    myTask.setPlace("Place: " + mplace);
                    myTask.setDescreption("description: " + mDescription);
                    myTask.setAmount("Amount: " + mAmount + preferences.getString("currency", "EGP"));
                    myTask.setDate("Date: " + mDay + "/" + mMonth + "/" + mYear);
                    myTask.setItemId(mid);

                    dataList.add(myTask);
                }

                acountAdapter = new AccountingAdapter(getActivity(), R.layout.transaction_row, dataList);
                listView.setAdapter(acountAdapter);
                acountAdapter.notifyDataSetChanged();

            } catch (Exception e) {

            }

        }

        public void FirstTab() {
            try {

                spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position == FILTER_SERVICES) {

                            FilterOfService();

                        } else if (position == FILTER_CLOTHES) {

                            FilterOfClothes();

                        } else if (position == FILTER_DELIVERY) {

                            FilterOfDdlivry();

                        } else if (position == FILTER_FIXES) {

                            FilterOfFixes();

                        } else if (position == FILTER_FOOD) {

                            FilterOfFood();

                        } else if (position == FILTER_INCOME) {

                            FilterOfIncome();

                        } else if (position == FILTER_OTHER) {

                            FilterOfOther();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position == SORT_TYPY) {

                            SortByType();

                        } else if (position == SORT_DATE) {

                            SortByDate();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.v("data:11", String.valueOf(spinnerFilter.getSelectedItem()));
                        if (search.length() == 0) {

                            Toast.makeText(getActivity(), "Please Enter Data You Want Search about", Toast.LENGTH_SHORT).show();

                        } else {
                            ProcessSeacrh();
                        }
                    }
                });

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder Mail = new AlertDialog.Builder(getActivity());
                        Mail.setItems(R.array.chooseOne, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (which == 0) {
                                    Intent i = new Intent(getActivity(), AddTransaction.class);
                                    i.putExtra("income", 0);
                                    startActivity(i);

                                } else if (which == 1) {
                                    Intent i = new Intent(getActivity(), AddTransaction.class);
                                    i.putExtra("income", 1);
                                    startActivity(i);

                                } else {
                                    Intent i = new Intent(getActivity(), AddTransaction.class);
                                    i.putExtra("income", 2);
                                    startActivity(i);
                                }
                            } // End Of AlertDialog
                        });
                        Mail.setTitle("Choose Transaction");
                        Mail.setCancelable(true);
                        Mail.show();

                    }
                });
            } catch (Exception e) {

            }

        }

        public void SecondTab() {
            try {


                GetBalance();
            } catch (Exception e) {

            }
//            Spinner year = (Spinner) layout.findViewById(R.id.spinnerMonth);
//            final TextView balance = (TextView) layout.findViewById(R.id.ShowBalance);
//
//            year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    //    db = new DatabaseHelper(getActivity());
//                    try {
//
//
//                        ListAmountOfPlannedofbalance = new ArrayList<>();
//
//                        ArrayList<MyTask> transactionListPlannedofbalance = db.FilterByPlannedTransaction();
//                        for (int j = 0; j < transactionListPlannedofbalance.size(); j++) {
//                            String mPlannedAmount = transactionListPlannedofbalance.get(j).getAmount();
//                            ListAmountOfPlannedofbalance.add(Integer.parseInt(mPlannedAmount));
//                        }
//
////                        balance.setText(" " + position);
//                        ListAmountOfIncomeofbalance = new ArrayList<>();
//                        ArrayList<MyTask> transactionListIncomeofbalance = db.FilterByIncomeTransaction();
//                        for (int j = 0; j < transactionListIncomeofbalance.size(); j++) {
//                            String mIncomAmount = transactionListIncomeofbalance.get(j).getAmount();
//                            ListAmountOfIncomeofbalance.add(Integer.parseInt(mIncomAmount));
//
//                        }
//
//
//                        ListAmountOfExpensess2ofbalance = new ArrayList<>();
//                        ArrayList<MyTask> transactionListexpensessofbalance = db.FilterByExpensessTransaction();
//                        for (int j = 0; j < transactionListexpensessofbalance.size(); j++) {
//                            String mExpensessAmount = transactionListexpensessofbalance.get(j).getAmount();
//                            ListAmountOfExpensess2ofbalance.add(Integer.parseInt(mExpensessAmount));
//                        }
//
////                        Toast.makeText(getActivity(), "of Planned Amount = " + ListAmountOfPlannedofbalance.get(position), Toast.LENGTH_SHORT).show();
////                        Toast.makeText(getActivity(), "of Income Amount = " + ListAmountOfIncomeofbalance.get(position), Toast.LENGTH_SHORT).show();
////                        Toast.makeText(getActivity(), "of Expenses Amount = " + ListAmountOfExpensess2ofbalance.get(position), Toast.LENGTH_SHORT).show();
//
//                        int ik = ((ListAmountOfIncomeofbalance.get(position)) - ((ListAmountOfExpensess2ofbalance.get(position) + ListAmountOfPlannedofbalance.get(position))));
//
//                        balance.setText(" " + ik);
//
//                    } catch (Exception e) {
//
//                    }
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });
        }

        public void TheridTab() {

            try {
                GetGraphExpenses();

                GetGraphBalance();

            } catch (Exception e) {

            }
        }

        public void FourithTab() {

            try {


                last12MonthType = (ListView) layout.findViewById(R.id.typeExpenses);
                last12MonthPlace = (ListView) layout.findViewById(R.id.placeExpenses);

                dataList3.clear();
                dataList2.clear();
                db = new DatabaseHelper(getActivity());

                for (int i = 1; i <= 12; i++) {
                    ArrayList<MyTask> transactionList = db.FilterByExpensessTransaction(i, preferences.getString("costumerName", ""));
                    myTask = new MyTask();
                    for (int j = 0; j < transactionList.size(); j++) {

                        String mplace = transactionList.get(j).getPlace();


                        myTask.setPlace(mplace);


                        dataList3.add(myTask);
                    }

                    for (int j = 0; j < transactionList.size(); j++) {

                        String mType = transactionList.get(j).getType();

                        myTask.setType(mType);


                        dataList2.add(myTask);
                    }
                }
                last12MonthAdapter1 = new Last12MonthAdapter(getActivity(), R.layout.last12month_row, dataList2);
                last12MonthType.setAdapter(last12MonthAdapter1);
                last12MonthAdapter1.notifyDataSetChanged();


                last12MonthAdapter2 = new Last12MonthAdapterExpenses(getActivity(), R.layout.last12month_row, dataList3);
                last12MonthPlace.setAdapter(last12MonthAdapter2);
                last12MonthAdapter2.notifyDataSetChanged();

            } catch (Exception e) {

            }
        }

        public void FilterOfService() {
            try {


                dataList.clear();
                db = new DatabaseHelper(getActivity());
                ArrayList<MyTask> transactionList = db.FilterByservicesOrLease(preferences.getString("costumerName", ""));
                for (int j = 0; j < transactionList.size(); j++) {

                    String mtype = transactionList.get(j).getType();
                    String mplace = transactionList.get(j).getPlace();
                    String mDescription = transactionList.get(j).getDescreption();
                    String mAmount = transactionList.get(j).getAmount();
                    Integer mDay = transactionList.get(j).getDay();
                    Integer mMonth = transactionList.get(j).getMonth();
                    Integer mYear = transactionList.get(j).getYear();
                    int mid = transactionList.get(j).getItemId();

                    myTask = new MyTask();
                    myTask.setType(mtype);
                    myTask.setPlace("Place: " + mplace);
                    myTask.setDescreption("description: " + mDescription);
                    myTask.setAmount("Amount: " + mAmount + preferences.getString("currency", "EGP"));
                    myTask.setDate("Date: " + mDay + "/" + mMonth + "/" + mYear);
                    myTask.setItemId(mid);

                    dataList.add(myTask);
                }

                acountAdapter = new AccountingAdapter(getActivity(), R.layout.transaction_row, dataList);
                listView.setAdapter(acountAdapter);
                acountAdapter.notifyDataSetChanged();
            } catch (Exception e) {

            }
        }

        public void FilterOfClothes() {
            try {

                dataList.clear();
                db = new DatabaseHelper(getActivity());
                ArrayList<MyTask> transactionList = db.FilterByClothes(preferences.getString("costumerName", ""));
                for (int j = 0; j < transactionList.size(); j++) {

                    String mtype = transactionList.get(j).getType();
                    String mplace = transactionList.get(j).getPlace();
                    String mDescription = transactionList.get(j).getDescreption();
                    String mAmount = transactionList.get(j).getAmount();
                    Integer mDay = transactionList.get(j).getDay();
                    Integer mMonth = transactionList.get(j).getMonth();
                    Integer mYear = transactionList.get(j).getYear();
                    int mid = transactionList.get(j).getItemId();

                    myTask = new MyTask();
                    myTask.setType(mtype);
                    myTask.setPlace("Place: " + mplace);
                    myTask.setDescreption("description: " + mDescription);
                    myTask.setAmount("Amount: " + mAmount + preferences.getString("currency", "EGP"));
                    myTask.setDate("Date: " + mDay + "/" + mMonth + "/" + mYear);
                    myTask.setItemId(mid);

                    dataList.add(myTask);
                }

                acountAdapter = new AccountingAdapter(getActivity(), R.layout.transaction_row, dataList);
                listView.setAdapter(acountAdapter);
                acountAdapter.notifyDataSetChanged();
            } catch (Exception e) {

            }
        }

        public void FilterOfDdlivry() {
            try {


                dataList.clear();
                db = new DatabaseHelper(getActivity());
                ArrayList<MyTask> transactionList = db.FilterByDelivery(preferences.getString("costumerName", ""));
                for (int j = 0; j < transactionList.size(); j++) {

                    String mtype = transactionList.get(j).getType();
                    String mplace = transactionList.get(j).getPlace();
                    String mDescription = transactionList.get(j).getDescreption();
                    String mAmount = transactionList.get(j).getAmount();
                    Integer mDay = transactionList.get(j).getDay();
                    Integer mMonth = transactionList.get(j).getMonth();
                    Integer mYear = transactionList.get(j).getYear();
                    int mid = transactionList.get(j).getItemId();


                    myTask = new MyTask();
                    myTask.setType(mtype);
                    myTask.setPlace("Place: " + mplace);
                    myTask.setDescreption("description: " + mDescription);
                    myTask.setAmount("Amount: " + mAmount + preferences.getString("currency", "EGP"));
                    myTask.setDate("Date: " + mDay + "/" + mMonth + "/" + mYear);
                    myTask.setItemId(mid);

                    dataList.add(myTask);

                    dataList.add(myTask);
                }

                acountAdapter = new AccountingAdapter(getActivity(), R.layout.transaction_row, dataList);
                listView.setAdapter(acountAdapter);
                acountAdapter.notifyDataSetChanged();
            } catch (Exception e) {

            }
        }

        public void FilterOfFixes() {
            try {


                dataList.clear();
                db = new DatabaseHelper(getActivity());
                ArrayList<MyTask> transactionList = db.FilterByFix(preferences.getString("costumerName", ""));
                for (int j = 0; j < transactionList.size(); j++) {

                    String mtype = transactionList.get(j).getType();
                    String mplace = transactionList.get(j).getPlace();
                    String mDescription = transactionList.get(j).getDescreption();
                    String mAmount = transactionList.get(j).getAmount();
                    Integer mDay = transactionList.get(j).getDay();
                    Integer mMonth = transactionList.get(j).getMonth();
                    Integer mYear = transactionList.get(j).getYear();
                    int mid = transactionList.get(j).getItemId();

                    myTask = new MyTask();
                    myTask = new MyTask();
                    myTask.setType(mtype);
                    myTask.setPlace("Place: " + mplace);
                    myTask.setDescreption("description: " + mDescription);
                    myTask.setAmount("Amount: " + mAmount + preferences.getString("currency", "EGP"));
                    myTask.setDate("Date: " + mDay + "/" + mMonth + "/" + mYear);
                    myTask.setItemId(mid);

                    dataList.add(myTask);
                }

                acountAdapter = new AccountingAdapter(getActivity(), R.layout.transaction_row, dataList);
                listView.setAdapter(acountAdapter);
                acountAdapter.notifyDataSetChanged();
            } catch (Exception e) {

            }
        }

        public void FilterOfFood() {
            try {


                dataList.clear();
                db = new DatabaseHelper(getActivity());
                ArrayList<MyTask> transactionList = db.FilterByFood(preferences.getString("costumerName", ""));
                for (int j = 0; j < transactionList.size(); j++) {

                    String mtype = transactionList.get(j).getType();
                    String mplace = transactionList.get(j).getPlace();
                    String mDescription = transactionList.get(j).getDescreption();
                    String mAmount = transactionList.get(j).getAmount();
                    Integer mDay = transactionList.get(j).getDay();
                    Integer mMonth = transactionList.get(j).getMonth();
                    Integer mYear = transactionList.get(j).getYear();
                    int mid = transactionList.get(j).getItemId();

                    myTask = new MyTask();
                    myTask.setType(mtype);
                    myTask.setPlace("Place: " + mplace);
                    myTask.setDescreption("description: " + mDescription);
                    myTask.setAmount("Amount: " + mAmount + preferences.getString("currency", "EGP"));
                    myTask.setDate("Date: " + mDay + "/" + mMonth + "/" + mYear);
                    myTask.setItemId(mid);

                    dataList.add(myTask);
                }

                acountAdapter = new AccountingAdapter(getActivity(), R.layout.transaction_row, dataList);
                listView.setAdapter(acountAdapter);
                acountAdapter.notifyDataSetChanged();
            } catch (Exception e) {

            }
        }// End Of Filter By Food

        public void FilterOfIncome() {
            try {


                dataList.clear();
                db = new DatabaseHelper(getActivity());
                ArrayList<MyTask> transactionList = db.FilterByIncomeTransaction(preferences.getString("costumerName", ""));
                for (int j = 0; j < transactionList.size(); j++) {

                    String mtype = transactionList.get(j).getType();
                    String mplace = transactionList.get(j).getPlace();
                    String mDescription = transactionList.get(j).getDescreption();
                    String mAmount = transactionList.get(j).getAmount();
                    Integer mDay = transactionList.get(j).getDay();
                    Integer mMonth = transactionList.get(j).getMonth();
                    Integer mYear = transactionList.get(j).getYear();
                    int mid = transactionList.get(j).getItemId();

                    myTask = new MyTask();
                    myTask.setType(mtype);
                    myTask.setPlace("Place: " + mplace);
                    myTask.setDescreption("description: " + mDescription);
                    myTask.setAmount("Amount: " + mAmount + preferences.getString("currency", "EGP"));
                    myTask.setDate("Date: " + mDay + "/" + mMonth + "/" + mYear);
                    myTask.setItemId(mid);

                    dataList.add(myTask);
                }

                acountAdapter = new AccountingAdapter(getActivity(), R.layout.transaction_row, dataList);
                listView.setAdapter(acountAdapter);
                acountAdapter.notifyDataSetChanged();
            } catch (Exception e) {

            }
        }// End Of Filter By Income

        public void FilterOfOther() {
            try {


                dataList.clear();
                db = new DatabaseHelper(getActivity());
                ArrayList<MyTask> transactionList = db.FilterByOther(preferences.getString("costumerName", ""));
                for (int j = 0; j < transactionList.size(); j++) {

                    String mtype = transactionList.get(j).getType();
                    String mplace = transactionList.get(j).getPlace();
                    String mDescription = transactionList.get(j).getDescreption();
                    String mAmount = transactionList.get(j).getAmount();
                    Integer mDay = transactionList.get(j).getDay();
                    Integer mMonth = transactionList.get(j).getMonth();
                    Integer mYear = transactionList.get(j).getYear();
                    int mid = transactionList.get(j).getItemId();

                    myTask = new MyTask();
                    myTask.setType(mtype);
                    myTask.setPlace("Place: " + mplace);
                    myTask.setDescreption("description: " + mDescription);
                    myTask.setAmount("Amount: " + mAmount + preferences.getString("currency", "EGP"));
                    myTask.setDate("Date: " + mDay + "/" + mMonth + "/" + mYear);
                    myTask.setItemId(mid);

                    dataList.add(myTask);
                }

                acountAdapter = new AccountingAdapter(getActivity(), R.layout.transaction_row, dataList);
                listView.setAdapter(acountAdapter);
                acountAdapter.notifyDataSetChanged();
            } catch (Exception e) {

            }
        }// End Of Filter By Other

        public void SortByType() {
            try {


                dataList.clear();
                db = new DatabaseHelper(getActivity());
                ArrayList<MyTask> transactionList = db.getAllTransactionOrderByType(preferences.getString("costumerName", ""));
                for (int j = 0; j < transactionList.size(); j++) {

                    String mtype = transactionList.get(j).getType();
                    String mplace = transactionList.get(j).getPlace();
                    String mDescription = transactionList.get(j).getDescreption();
                    String mAmount = transactionList.get(j).getAmount();
                    Integer mDay = transactionList.get(j).getDay();
                    Integer mMonth = transactionList.get(j).getMonth();
                    Integer mYear = transactionList.get(j).getYear();
                    int mid = transactionList.get(j).getItemId();

                    myTask = new MyTask();
                    myTask.setType(mtype);
                    myTask.setPlace("Place: " + mplace);
                    myTask.setDescreption("description: " + mDescription);
                    myTask.setAmount("Amount: " + mAmount + preferences.getString("currency", "EGP"));
                    myTask.setDate("Date: " + mDay + "/" + mMonth + "/" + mYear);
                    myTask.setItemId(mid);

                    dataList.add(myTask);

                    dataList.add(myTask);
                }

                acountAdapter = new AccountingAdapter(getActivity(), R.layout.transaction_row, dataList);
                listView.setAdapter(acountAdapter);
                acountAdapter.notifyDataSetChanged();
            } catch (Exception e) {

            }
        }// End Of Sort By Type

        public void SortByDate() {
            try {


                dataList.clear();
                db = new DatabaseHelper(getActivity());
                ArrayList<MyTask> transactionList = db.getAllTransactionOrderByMonth(preferences.getString("costumerName", ""));
                for (int j = 0; j < transactionList.size(); j++) {

                    String mtype = transactionList.get(j).getType();
                    String mplace = transactionList.get(j).getPlace();
                    String mDescription = transactionList.get(j).getDescreption();
                    String mAmount = transactionList.get(j).getAmount();
                    Integer mDay = transactionList.get(j).getDay();
                    Integer mMonth = transactionList.get(j).getMonth();
                    Integer mYear = transactionList.get(j).getYear();
                    int mid = transactionList.get(j).getItemId();

                    myTask = new MyTask();
                    myTask.setType(mtype);
                    myTask.setPlace("Place: " + mplace);
                    myTask.setDescreption("description: " + mDescription);
                    myTask.setAmount("Amount: " + mAmount + preferences.getString("currency", "EGP"));
                    myTask.setDate("Date: " + mDay + "/" + mMonth + "/" + mYear);
                    myTask.setItemId(mid);

                    dataList.add(myTask);

                    dataList.add(myTask);
                }

                acountAdapter = new AccountingAdapter(getActivity(), R.layout.transaction_row, dataList);
                listView.setAdapter(acountAdapter);
                acountAdapter.notifyDataSetChanged();
            } catch (Exception e) {

            }
        }// End Of Sort By Date

        public void ProcessSeacrh() {
            try {


                dataList.clear();
                db = new DatabaseHelper(getActivity());
                ArrayList<MyTask> transactionList = db.Search(search.getText().toString().trim(), preferences.getString("costumerName", ""));
                if (transactionList.size() != 0) {
                    for (int j = 0; j < transactionList.size(); j++) {
                        String mtype = transactionList.get(j).getType();
                        String mplace = transactionList.get(j).getPlace();
                        String mDescription = transactionList.get(j).getDescreption();
                        String mAmount = transactionList.get(j).getAmount();
                        Integer mDay = transactionList.get(j).getDay();
                        Integer mMonth = transactionList.get(j).getMonth();
                        Integer mYear = transactionList.get(j).getYear();
                        int mid = transactionList.get(j).getItemId();
                        myTask = new MyTask();
                        myTask.setType(mtype);
                        myTask.setPlace("Place: " + mplace);
                        myTask.setDescreption("description: " + mDescription);
                        myTask.setAmount("Amount: " + mAmount + preferences.getString("currency", "EGP"));
                        myTask.setDate("Date: " + mDay + "/" + mMonth + "/" + mYear);
                        myTask.setItemId(mid);

                        dataList.add(myTask);
                    }

                    acountAdapter = new AccountingAdapter(getActivity(), R.layout.transaction_row, dataList);
                    listView.setAdapter(acountAdapter);
                    acountAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "NO DATA", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {

            }
        }// End Of Process search

        public void GetBalance() {
            try {


                balance = (TextView) layout.findViewById(R.id.ShowBalance);
                year = (Spinner) layout.findViewById(R.id.spinnerMonth);
                year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        try {

                            db = new DatabaseHelper(getActivity());
                            ArrayList<MyTask> transactionList = db.SearchByDate(position + 1, preferences.getString("costumerName", ""));
                            Log.v("data101", String.valueOf(transactionList.size()));
                            if (transactionList.size() == 0) {
                                balance.setText("0");
                            } else {
                                for (int j = 0; j < transactionList.size(); j++) {
                                    String getTransaction = transactionList.get(j).getTransaction().trim();
                                    Log.v("data30", getTransaction);

                                    switch (getTransaction) {
                                        case "income":
                                            String mIncomeAmount = transactionList.get(j).getAmount();
                                            totalOfIncome += Integer.parseInt(mIncomeAmount);
                                            Log.v("data03", String.valueOf(totalOfIncome));
                                            break;
                                        case "Actual_Expenses":
                                            String mExpensesAmount = transactionList.get(j).getAmount();
                                            totalOfExpenses += Integer.parseInt(mExpensesAmount);
                                            Log.v("data02", String.valueOf(totalOfExpenses));
                                            break;
                                        case "Planned_Expenses":
                                            String mPlannedAmount = transactionList.get(j).getAmount();
                                            totalOfPlanned += Integer.parseInt(mPlannedAmount);
                                            Log.v("data01", String.valueOf(totalOfPlanned));
                                            break;
                                    }
                                }
                                int Balance = totalOfIncome - (totalOfExpenses + totalOfPlanned);
                                Log.v("data0001", String.valueOf(Balance));
                                totalOfIncome = 0;
                                totalOfExpenses = 0;
                                totalOfPlanned = 0;
                                String TotalOfBalance = String.valueOf(Balance);
                                balance.setText(TotalOfBalance + " " + preferences.getString("currency", "EGP"));

                            }
                        } catch (Exception e) {

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            } catch (Exception e) {

            }
        }

        public void GetGraphBalance() {
            try {


                GraphView graphBalance = (GraphView) layout.findViewById(R.id.graphBalances);
                seriesOfBalance = new LineGraphSeries<DataPoint>();
                double Balance;

                for (int k = 1; k <= 12; k++) {

                    db = new DatabaseHelper(getActivity());
                    ArrayList<MyTask> transactionList = db.SearchByDate(k, preferences.getString("costumerName", ""));
                    if (transactionList.size() == 0) {
                        Balance = 0;
                        seriesOfBalance.appendData(new DataPoint(k, Balance), true, 12);
                        graphBalance.addSeries(seriesOfBalance);

                    } else {
                        for (int j = 0; j < transactionList.size(); j++) {
                            String getTransaction = transactionList.get(j).getTransaction().trim();
                            Log.v("data30", getTransaction);

                            switch (getTransaction) {
                                case "income":
                                    String mIncomeAmount = transactionList.get(j).getAmount();
                                    totalOfIncome += Integer.parseInt(mIncomeAmount);
                                    Log.v("data03", String.valueOf(totalOfIncome));
                                    break;
                                case "Actual_Expenses":
                                    String mExpensesAmount = transactionList.get(j).getAmount();
                                    totalOfExpenses += Integer.parseInt(mExpensesAmount);
                                    Log.v("data02", String.valueOf(totalOfExpenses));
                                    break;
                                case "Planned_Expenses":
                                    String mPlannedAmount = transactionList.get(j).getAmount();
                                    totalOfPlanned += Integer.parseInt(mPlannedAmount);
                                    Log.v("data01", String.valueOf(totalOfPlanned));
                                    break;
                            }
                        }

                        Balance = totalOfIncome - (totalOfExpenses + totalOfPlanned);
                        Log.v("data0001", String.valueOf(Balance));
                        totalOfIncome = 0;
                        totalOfExpenses = 0;
                        totalOfPlanned = 0;


                        graphBalance.setTitle(" Last 12 months balance ");
                        graphBalance.getViewport().setMinX(0);
                        graphBalance.getViewport().setMinY(-3000);
                        graphBalance.getViewport().setMaxY(3000);

                        seriesOfBalance.appendData(new DataPoint(k, Balance), true, 12);
                        graphBalance.addSeries(seriesOfBalance);
                    }

                }

            } catch (Exception e) {

            }
        }

        public void GetGraphExpenses() {
            try {


                GraphView graphExpenses = (GraphView) layout.findViewById(R.id.graphExpensess);
                seriesOfExpenses = new LineGraphSeries<DataPoint>();
                double Expenses;

                for (int k = 1; k <= 12; k++) {

                    db = new DatabaseHelper(getActivity());
                    ArrayList<MyTask> transactionList = db.SearchByDate(k, preferences.getString("costumerName", ""));
                    Log.v("data111", String.valueOf(transactionList.size()));
                    if (transactionList.size() == 0) {
                        seriesOfExpenses.appendData(new DataPoint(k, 0), true, 12);
                    } else {
                        for (int j = 0; j < transactionList.size(); j++) {
                            String getTransaction = transactionList.get(j).getTransaction().trim();
                            Log.v("data30", getTransaction);

                            switch (getTransaction) {
                                case "Actual_Expenses":
                                    String mExpensesAmount = transactionList.get(j).getAmount();
                                    totalOfExpenses += Integer.parseInt(mExpensesAmount);
                                    Log.v("data02", String.valueOf(totalOfExpenses));
                                    break;
                                case "Planned_Expenses":
                                    String mPlannedAmount = transactionList.get(j).getAmount();
                                    totalOfPlanned += Integer.parseInt(mPlannedAmount);
                                    Log.v("data01", String.valueOf(totalOfPlanned));
                                    break;
                            }
                        }
                        Expenses = totalOfExpenses + totalOfPlanned;

                        totalOfExpenses = 0;
                        totalOfPlanned = 0;
                        graphExpenses.setTitle(" Last 12 months Expenses ");
                        graphExpenses.getViewport().setMinX(0);
                        graphExpenses.getViewport().setMinY(0);
                        graphExpenses.getViewport().setMaxY(3000);
                        graphExpenses.getViewport().setYAxisBoundsManual(true);
                        graphExpenses.getViewport().setXAxisBoundsManual(true);
                        seriesOfExpenses.appendData(new DataPoint(k, Expenses), true, 12);
                        graphExpenses.addSeries(seriesOfExpenses);
                    }
                }
            } catch (Exception e) {

            }
        }
    }
}
