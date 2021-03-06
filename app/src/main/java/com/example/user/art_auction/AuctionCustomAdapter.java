package com.example.user.art_auction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;

/**
 * Created by user on 19/10/2017.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;



class AuctionCustomAdapter extends ArrayAdapter<Auction> {

    Date now = new Date();
    Date startAuction, endAuction;

    public AuctionCustomAdapter(Context context, Auction[] auctions) {
        super(context, R.layout.hp_row, auctions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // default -  return super.getView(position, convertView, parent);
        // add the layout
        LayoutInflater myCustomInflater = LayoutInflater.from(getContext());
        View customView = myCustomInflater.inflate(R.layout.hp_row, parent, false);
        // get references.
        final Auction singleAuctionItem = getItem(position);
        TextView itemText = (TextView) customView.findViewById(R.id.item_text);
        Button b1 = (Button) customView.findViewById(R.id.enter_btn);
        b1.setTag(singleAuctionItem);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auction a = (Auction) v.getTag();
                Intent myIntent = new Intent(v.getContext(), AuctionActivity.class);
                myIntent.putExtra("Auction", singleAuctionItem);
                v.getContext().startActivity(myIntent);
            }
        });

        startAuction = singleAuctionItem.getStartAuction();
        endAuction = singleAuctionItem.getEndAuction();

        Button b2 = (Button) customView.findViewById(R.id.timer_btn);
        String dateDiff;
        if (now.after(startAuction) && now.before(endAuction)) {
//            b2.setBackgroundColor("00aeff");
            b2.setClickable(false);
            dateDiff = getDateDiff(now, endAuction);
            b2.setText("Ends in: " + dateDiff);
        }
        else if (now.after(startAuction) && now.after(endAuction)){
            dateDiff = getDateDiff(startAuction,now);
            b2.setText("Starts in: " + dateDiff);
        }
        else {
            b1.setClickable(false);
            b1.setBackgroundColor(220);
            b2.setText("Close");

        }


        startAuction = singleAuctionItem.getStartAuction();
        endAuction = singleAuctionItem.getEndAuction();
        ImageView auctionImage = (ImageView) customView.findViewById(R.id.hp_main_image);
        // dynamically update the text from the array
        itemText.setText(singleAuctionItem.getTitle());
        // using the same image every time
        auctionImage.setImageResource(R.drawable.download);
        // Now we can finally return our custom View or custom item
        return customView;
    }

    private String getDateDiff(Date startDate, Date endDate) {
        //in milliseconds
        long diff = endDate.getTime() - startDate.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return "" + Math.abs(diffDays) + " Days "+ Math.abs(diffHours) + " Hours " + Math.abs(diffMinutes)+ " Minutes " + Math.abs(diffSeconds) + " Seconds";
    }
}