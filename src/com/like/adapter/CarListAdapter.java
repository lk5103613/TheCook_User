package com.like.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.dcjd.cook.R;
import com.like.entity.ShoppingCartEntity;
import com.like.network.MyNetworkUtil;
import com.like.storage.ShoppingCartManager;

public class CarListAdapter extends SimpleAdapter<ShoppingCartEntity>{
	
	private Context mContext;
	private List<ShoppingCartEntity> mCars;
	private ImageLoader mImageLoader;
    private ShoppingCartManager mManager;
    private Map<Integer, Boolean> mCheckboxes = new HashMap<Integer, Boolean>();
    private CheckListener mListener;
    
	public CarListAdapter(Context context, List<ShoppingCartEntity> datas, CheckListener listener) {
		super(context, datas);
		this.mContext = context;
		this.mListener = listener;
		this.mCars = datas;
		mImageLoader = MyNetworkUtil.getInstance(context).getImageLoader();
        mManager = ShoppingCartManager.getInstance(mContext);
	}

    public void setList(List<ShoppingCartEntity> entities) {
        this.mCars = entities;
    }
    
    public void updateList(List<ShoppingCartEntity> entities) {
    	update(entities);
    	this.mCars = entities;
    	notifyDataSetChanged();
    }

	@Override
	public int getItemResourceId() {
		return R.layout.car_item;
	}
	
	public void selectAll(boolean isSelectAll) {
		for(int i=0; i<mCars.size(); i++) {
			int id = mCars.get(i).id;
			if(isSelectAll) {
				mCheckboxes.put(id, true);
			}
			else {
				mCheckboxes.put(id, false);
			}
		}
		mListener.check(getSelectedEntities());
		notifyDataSetChanged();
	}
	
	public List<ShoppingCartEntity> getSelectedEntities() {
		List<ShoppingCartEntity> result = new ArrayList<ShoppingCartEntity>();
		for(int i : mCheckboxes.keySet()) {
			for(int j=0; j<mCars.size(); j++) {
				if(mCheckboxes.get(i)) {
					if(mCars.get(j).id == i) {
						result.add(mCars.get(j));
					}
				} else {
					continue;
				}
			}
		}
		return result;
	}
	
	public interface CheckListener {
		void check(List<ShoppingCartEntity> selectedEntities);
	}
	
	@Override
	public void bindData(final int position, View convertView,ViewHolder holder) {
        final ShoppingCartEntity entity = mCars.get(position);
        final CheckBox check = holder.findView(R.id.check);
		ImageView img = holder.findView(R.id.tc_img);
		TextView name = holder.findView(R.id.tc_name);
		TextView money = holder.findView(R.id.money);
		final TextView count = holder.findView(R.id.count);
        ViewGroup delete = holder.findView(R.id.delete);
        ViewGroup jia = holder.findView(R.id.jia);
        ViewGroup jian = holder.findView(R.id.jian);
        ImageView showImg = holder.findView(R.id.tc_img);
        ViewGroup checkContainer = holder.findView(R.id.check_container);
        
        final int entityId = entity.id;
        if(mCheckboxes.get(entity.id) == null) {
        	check.setChecked(false);
        	mCheckboxes.put(entityId, false);
        } else {
        	check.setChecked(mCheckboxes.get(entityId));
        }
        
        checkContainer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				check.performClick();
			}
		});
        
        check.setTag(entity.id);
        check.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = (Integer) v.getTag();
				boolean value = mCheckboxes.get(id);
				check.setChecked(!value);
				mCheckboxes.put(id, !value);
				if(mListener != null)
					mListener.check(getSelectedEntities());
			}
		});
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mManager.remove(entity);
                mCars.remove(entity);
                notifyDataSetChanged();
                if(mListener != null)
                	mListener.check(getSelectedEntities());
            }
        });
        jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cnt = Integer.valueOf(count.getText().toString());
                count.setText((cnt + 1) + "");
                entity.cnt = cnt + 1;
                mManager.update(entity);
                if(mListener != null)
                	mListener.check(getSelectedEntities());
            }
        });
        jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cnt = Integer.valueOf(count.getText().toString());
                if(cnt == 0) 
                	return;
                count.setText((cnt - 1) + "");
                entity.cnt = cnt - 1;
                mManager.update(entity);
                if(mListener != null)
                	mListener.check(getSelectedEntities());
            }
        });

//		img.setImageResource(carEntity.getImg());
        mImageLoader.get(entity.img, ImageLoader.getImageListener(img, R.color.white, R.color.white));
		name.setText(entity.name);
		money.setText(entity.price+"");
		count.setText(entity.cnt+"");
	}

}
