package com.like.entity;

import java.util.List;

public class TCDetail {
	
	public int packid;
	public String packname;
	public String price;
	public String add_time;
	public String img_path;
	public String sold_cnt;
	public int style;
	
	public List<Comment> commentList;
	
	public class Comment {
		public int comment_id;
		public String content;
		public String nickname;
		public String img0_path;
		public String img1_path;
		public String img2_path;
		public String score0;
		public String score1;
		public String score2;
		public String score3;
		public String uid;
		public int cook_id;
		public String add_time;
		public int pack_id;
		public String user_avatar;
	}

}
