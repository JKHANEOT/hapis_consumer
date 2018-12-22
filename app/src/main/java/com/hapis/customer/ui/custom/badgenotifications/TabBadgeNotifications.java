/**
 * 2017  Badge Tab for Android (http://www..com)
 *
 * @author – Egemen Mede (DeliPenguen - http://www.delipenguen.com)
 * @version – 1.0
 * @since - 03.03.2017
 */

package com.hapis.customer.ui.custom.badgenotifications;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hapis.customer.R;

/**
 * The type  badge tab.
 */
public class TabBadgeNotifications extends TabLayout {

    private final SparseArray<BadgeTabBuilder> mTabBuilders = new SparseArray<>();

    /**
     * Instantiates a new  badge tab.
     *
     * @param context the context
     */
    public TabBadgeNotifications(Context context) {
        super(context);
    }

    /**
     * Instantiates a new  badge tab.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public TabBadgeNotifications(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Instantiates a new  badge tab.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
    public TabBadgeNotifications(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *  badge tab builder.
     *
     * @param position Tab'in konumunu integer olarak belirtir.
     * @return Uzerinde islem yapilacak tab'i dondurur.
     */
    public BadgeTabBuilder selectBadgeTab(int position) {
        Tab tab = getTabAt(position);

        if (tab == null) throw new IllegalArgumentException("Tab null olmamali.");

        BadgeTabBuilder builder = mTabBuilders.get(tab.getPosition());
        if (builder == null) {
            builder = new BadgeTabBuilder(this, tab);
            mTabBuilders.put(tab.getPosition(), builder);
        }

        return builder;
    }

    /**
     * The type BadgeTabBuilder.
     */
    public static final class BadgeTabBuilder {
        @Nullable
        private final View mView;
        private final Context mContext;
        private final TabLayout.Tab mTab;
        private TextView mTabBadgeLayout;
       // private Integer mTabTitleColorFilter;
        private ColorStateList mTabTitleColorFilters;
        private Integer mTabIconColor;
        private Integer mTabBadgeBgColor;
        private Integer mTabBadgeTextColor;
        private Integer mTabBadgeCornerRadius;
        private Integer mTabBadgeStrokeWidth;
        private Integer mTabBadgeStrokeColor;
        private Integer mTabIconToTitle = 1;
        private GradientDrawable badgeRedraw;
        private TextView mTabTitle;
        private String mTabTitleValue = "";
        private int mBadgeCount = Integer.MIN_VALUE;
        private String mTabIconDirectionValue = "LEFT";
        private Drawable mTabIcon;
        private boolean mTabBadgeCountMore = false;
        private boolean mIsBadge = false;

        private BadgeTabBuilder(TabLayout parent, @NonNull TabLayout.Tab tab) {
            super();
            this.mContext = parent.getContext();
            this.mTab = tab;

            if (tab.getCustomView() != null) {
                this.mView = tab.getCustomView();
            } else {
                this.mView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.tab_badge_layout, parent, false);
            }

            if (mView != null) {
                this.mTabBadgeLayout = mView.findViewById(R.id.tabBadgeLayout);
                this.mTabTitle = mView.findViewById(R.id.tabTitleLayout);
            }

            if (this.mTabBadgeLayout != null) {
                if (mIsBadge) {
                    this.mIsBadge = mTabBadgeLayout.getVisibility() == View.VISIBLE;
                }
            }
        }

        /**
         * Tab badge BadgeTabBuilder.
         *
         * @param isBadge Tab icerisinde Badge olup olmamasini belirten degerdir. true/false degerlerini alabilir.
         * @return Deger set edilmis nesneyi dondurur.
         */
        public BadgeTabBuilder tabBadge(boolean isBadge) {
            mIsBadge = isBadge;
            return this;
        }

        /**
         * Tab title color BadgeTabBuilder.
         *
         * @param colors Tab metninin rengini belirten integer degeridir.
         * @return Deger set edilmis nesneyi dondurur.
         */
       /* public BadgeTabBuilder tabTitleColor(Integer color) {
            mTabTitleColorFilter = color;
            return this;
        }*/

        public BadgeTabBuilder tabTitleColorStateList(ColorStateList colors) {
            mTabTitleColorFilters = colors;
            return this;
        }

        /**
         * Tab badge count BadgeTabBuilder.
         *
         * @param count Badge icerisindeki rakami belirten integer degeridir.
         * @return Deger set edilmis nesneyi dondurur.
         */
        public BadgeTabBuilder tabBadgeCount(int count) {
            mBadgeCount = count;
            return this;
        }

        /**
         * Tab badge count more BadgeTabBuilder.
         *
         * @param isTabBadgeCountMore Badge'in gosterim seklini belirtir. true/false degeri alir. Degeri true ise 9'dan sonrasi "9+" olarak gosterilir. Aksi durumda sayinin tamami gosterilir.
         * @return Deger set edilmis nesneyi dondurur.
         */
        public BadgeTabBuilder tabBadgeCountMore(boolean isTabBadgeCountMore) {
            mTabBadgeCountMore = isTabBadgeCountMore;
            return this;
        }

        /**
         * Tab title BadgeTabBuilder.
         *
         * @param tabTitleText Tab metnini ifade eder.
         * @return Deger set edilmis nesneyi dondurur.
         */
        public BadgeTabBuilder tabTitle(String tabTitleText) {
            mTabTitleValue = tabTitleText;
            return this;
        }

        /**
         * Tab icon BadgeTabBuilder.
         *
         * @param drawableRes Tab'ta kullanilacak ikonun integer degeridir.
         * @return Deger set edilmis nesneyi dondurur.
         */
        public BadgeTabBuilder tabIcon(int drawableRes) {
            mTabIcon = getDrawableCompat(mContext, drawableRes);
            return this;
        }

        /**
         * Tab icon BadgeTabBuilder.
         *
         * @param drawableRes Tab'ta kullanilacak ikonun Drawable degeridir.
         * @return Deger set edilmis nesneyi dondurur.
         */
        public BadgeTabBuilder tabIcon(Drawable drawableRes) {
            mTabIcon = drawableRes;
            return this;
        }

        /**
         * Tab icon color BadgeTabBuilder.
         *
         * @param color Tab ikon rengini belirten integer degeridir.
         * @return Deger set edilmis nesneyi dondurur.
         */
        public BadgeTabBuilder tabIconColor(Integer color) {
            mTabIconColor = color;
            return this;
        }

        /**
         * Tab icon direction BadgeTabBuilder.
         *
         * @param tabIconDirectionText Tab icerisinde gosterilecek ikonun konumunu belirler. "LEFT" ve "RIGHT" olmak uzere 2 metin degeri alir.
         * @return Deger set edilmis nesneyi dondurur.
         */
        public BadgeTabBuilder tabIconDirection(String tabIconDirectionText) {
            mTabIconDirectionValue = tabIconDirectionText;
            return this;
        }

        /**
         * Tab icon to title BadgeTabBuilder.
         *
         * @param value Tab icerisindeki ikon ve metin arasindaki boslugu belirleyen Integer degeridir.
         * @return Deger set edilmis nesneyi dondurur.
         */
        public BadgeTabBuilder tabIconToTitle(Integer value) {
            mTabIconToTitle = value;
            return this;
        }

        /**
         * Tab badge bg color BadgeTabBuilder.
         *
         * @param color Badge'in arkaplan renginin integer degeridir.
         * @return Deger set edilmis nesneyi dondurur.
         */
        public BadgeTabBuilder tabBadgeBgColor(Integer color) {
            mTabBadgeBgColor = color;
            return this;
        }

        /**
         * Tab badge text color BadgeTabBuilder.
         *
         * @param color Badge'in metin renginin integer degeridir.
         * @return Deger set edilmis nesneyi dondurur.
         */
        public BadgeTabBuilder tabBadgeTextColor(Integer color) {
            mTabBadgeTextColor = color;
            return this;
        }

        /**
         * Tab badge corner radius BadgeTabBuilder.
         *
         * @param value Badge'in kose yaricap degerinin integer degeridir.
         * @return Deger set edilmis nesneyi dondurur.
         */
        public BadgeTabBuilder tabBadgeCornerRadius(Integer value) {
            mTabBadgeCornerRadius = value;
            return this;
        }

        /**
         * Tab badge stroke BadgeTabBuilder.
         *
         * @param strokeWidth Badge'in kenar cizgi kalinligini gosteren integer degeridir.
         * @param strokeColor Badge'in kenar cizgi rengini gosteren integer degeridir.
         * @return Deger set edilmis nesneyi dondurur.
         */
        public BadgeTabBuilder tabBadgeStroke(Integer strokeWidth, Integer strokeColor) {
            mTabBadgeStrokeWidth = strokeWidth;
            mTabBadgeStrokeColor = strokeColor;
            return this;
        }

        /**
         * Create  badge tab.
         */
        public void createBadgeTab() {
            if (mView == null) {
                return;
            }

            if (mTabIcon != null && mTabIconColor != null) {
                mTabIcon.setColorFilter(mContext.getResources().getColor(mTabIconColor), PorterDuff.Mode.SRC_IN);
            }

            if (mTabBadgeLayout != null) {
                badgeRedraw = (GradientDrawable) mTabBadgeLayout.getBackground().mutate();
                badgeRedraw.setShape(GradientDrawable.RECTANGLE);

                if (mTabBadgeCornerRadius != null) {
                    badgeRedraw.setCornerRadius(mTabBadgeCornerRadius);
                }

                if (mTabBadgeStrokeWidth != null && mTabBadgeStrokeColor != null) {
                    badgeRedraw.setStroke(mTabBadgeStrokeWidth, mContext.getResources().getColor(mTabBadgeStrokeColor));
                }

                if (mTabBadgeBgColor != null) {
                    badgeRedraw.setColor(mContext.getResources().getColor(mTabBadgeBgColor));
                }

                if (mTabBadgeTextColor != null) {
                    mTabBadgeLayout.setTextColor(mContext.getResources().getColor(mTabBadgeTextColor));
                }

                if (mIsBadge) {
                    mTabBadgeLayout.setVisibility(View.VISIBLE);
                    mTabBadgeLayout.setText(formattedBadgeView(mBadgeCount, mTabBadgeCountMore));
                } else {
                    mTabBadgeLayout.setVisibility(View.GONE);
                }
            }

            if (mTabTitle != null) {
                mTabTitle.setText(displayTabTitle(mTabTitleValue, mTabIconToTitle, mTabIconDirectionValue));

                /*if (mTabTitleColorFilter != null) {
                    mTabTitle.setTextColor(mContext.getResources().getColor(mTabTitleColorFilter));
                }*/

                if (mTabTitleColorFilters != null) {
                    mTabTitle.setTextColor(mTabTitleColorFilters);
                }

                if (mTabIconDirectionValue.equals("LEFT")) {
                    mTabTitle.setCompoundDrawablesWithIntrinsicBounds(mTabIcon, null, null, null);
                }
                if (mTabIconDirectionValue.equals("RIGHT")) {
                    mTabTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, mTabIcon, null);
                }
            }

            mTab.setCustomView(mView);
        }
    }

    /**
     * @param tabTitleValue         Tab'ta gosterilecek metin degeridir.
     * @param tabIconToTitle        Tab icerisindeki ikon ve metin arasindaki boslugu belirleyen Integer
     *                              degeridir.
     * @param tabIconDirectionValue Tab icerisinde gosterilecek ikonun konumunu belirler. "LEFT"
     *                              ve "RIGHT" olmak uzere 2 metin degeri alir.
     * @return Geriye Tab metnini onunde ya da arkasindaki bosluklarla birlikte dondurur.
     */
    private static String displayTabTitle(String tabTitleValue, Integer tabIconToTitle, String tabIconDirectionValue) {
        StringBuilder tabTitleBuilder = new StringBuilder();

        if (tabIconDirectionValue.equals("RIGHT")) {
            tabTitleBuilder.append(tabTitleValue);
        }

        for (int i = 0; i < tabIconToTitle; i++) {
            tabTitleBuilder.append(" ");
        }

        if (tabIconDirectionValue.equals("LEFT")) {
            tabTitleBuilder.append(tabTitleValue);
        }

        return tabTitleBuilder.toString();
    }

    /**
     * @param context     Islemin yapilacagi context'i belirtir.
     * @param drawableRes Drawable nesnesine donusturulecek olan kaynagin integer olarak gosterilen
     *                    halidir.
     * @return Geriye bir Drawable nesnesi dondurur.
     */
    private static Drawable getDrawableCompat(Context context, int drawableRes) {
        Drawable drawable = null;
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                drawable = context.getResources().getDrawable(drawableRes);
            } else {
                drawable = context.getResources().getDrawable(drawableRes, context.getTheme());
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return drawable;
    }

    /**
     * @param value              Bicimlendirilecek Badge sayi degerini integer olarak belirtir.
     * @param mTabBadgeCountMore Badge'in gosterim seklini belirtir. true/false degeri alir.
     *                           Degeri true ise 9'dan sonrasi "9+" olarak gosterilir.
     *                           Aksi durumda sayinin tamami gosterilir.
     * @return Geriye "18" ya da "9+" gibi bir metin degeri dondurur.
     */
    private static String formattedBadgeView(int value, boolean mTabBadgeCountMore) {
        if (0 != value && 0 < value && value < 10) {
            return Integer.toString(value);
        } else {
            if (mTabBadgeCountMore) {
                return "9+";
            } else {
                return Integer.toString(value);
            }
        }
    }

}