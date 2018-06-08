package info.isom.irrreebe;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
/**
 * Created by fablab on 2/7/2018.
 */

class AdminTasAdapter extends FragmentPagerAdapter
{

    public AdminTasAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                RequestFragment requestFragment = new RequestFragment();
                return requestFragment;
            case 1:
                chatsFragament chatsFragament = new chatsFragament();
                return chatsFragament;

            case 2:
                createFragment newuser =new createFragment();
                return  newuser;
            default:
                return null;
        }
    }

    @Override
    public int getCount()
        {
        return 3;
    }
        @Override
        public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "MESSAGE FROM PEAPLE";
            case 1:
                return "ADMIN POST";
            case 2:
                return "NEW USER ACCount";

            default:
                return null;

        }
    }
}
