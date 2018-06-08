package info.isom.irrreebe;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by fablab on 2/6/2018.
 */

class TabsPageAdapter extends FragmentStatePagerAdapter
{
    public TabsPageAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                RequestFragment requestFragment =new RequestFragment();
                return requestFragment;
            case 1:
                chatsFragament chatsFragament= new chatsFragament();
                return chatsFragament;
            case 2:
                friendFragment friendFragment= new friendFragment();
                return friendFragment;
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
                return "IBYANGOMBWA BYATOTRAGUWE";
            case 1:
                return "IBITEKEREZO";
            case 2:
                return "AMATANGAZO";
            default:
                return null;

        }
    }
}
