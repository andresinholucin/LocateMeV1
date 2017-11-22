package ec.edu.upse.locatemev1.controladores.tabsControl;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ec.edu.upse.locatemev1.R;

public class MenuFragment extends Fragment {
    private AppBarLayout appBar;
    private TabLayout tab;
    private ViewPager viewPage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu,container,false);

        View contenedor = (View) container.getParent();

        appBar = (AppBarLayout) contenedor.findViewById(R.id.appbar);
        tab = new TabLayout(getActivity());
        tab.setTabTextColors(Color.parseColor("#FFFFFF"),Color.parseColor("#FFFFFF"));
        appBar.addView(tab);

        //viewPage=(ViewPager) view.findViewById(R.id.pager);

        //viewPage.findViewById(R.id.pager);
        viewPage= (ViewPager)view.findViewById(R.id.pager);
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPage.setAdapter(pagerAdapter);
        tab.setupWithViewPager(viewPage);
        return view;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        appBar.removeView(tab);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        String[] tituloTabs={"Lista Tutoriado","Ubicación","Alertas"};

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0: return new TabTutoriadosFragment();
                case 1: return new TabUbicacionFragment();
                case 2: return new TabAlertasFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tituloTabs[position];
        }


    }
}
