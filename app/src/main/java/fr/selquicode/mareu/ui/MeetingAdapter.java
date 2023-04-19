package fr.selquicode.mareu.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.selquicode.mareu.data.model.Meeting;
import fr.selquicode.mareu.databinding.MeetingItemBinding;
import fr.selquicode.mareu.ui.viewModel.MeetingsViewState;

public class MeetingAdapter extends ListAdapter<MeetingsViewState, MeetingAdapter.ViewHolder> {


    public MeetingAdapter() {
        super(DIFF_CALLBACK);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewGroup layout;
        private final TextView date, subject, room, roomImgText, members;
        private final TextClock hour;
        private final ImageView deleteImg, circleColorRoom;

        public ViewHolder(@NonNull MeetingItemBinding binding) {
            super(binding.getRoot());

            layout = binding.meetingItemLyt;
            date = binding.dateMeeting;
            subject = binding.subjectMeeting;
            room = binding.placeMeeting;
            roomImgText = binding.roomImgText;
            hour = binding.timeMeeting;
            members = binding.participantsMeeting;
            deleteImg = binding.deleteBtn;
            circleColorRoom=binding.imageMeeting;
        }

        public void bind(MeetingsViewState item){
            date.setText(item.getDate());
            subject.setText(item.getSubject());
            room.setText(item.getRoomName());
            roomImgText.setText(item.getRoomName().charAt(0)+ "");
            circleColorRoom.setImageResource(item.getColorRoom());
            hour.setText(item.getHour());
            members.setText(item.getMeetingMembers());
            //delete.setOnClickListener
            //itemView.setOnclickListener
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MeetingItemBinding binding = MeetingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    /**
     * class that compare two item and their content to update them if it's necessary
     */
    public static final DiffUtil.ItemCallback<MeetingsViewState> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MeetingsViewState>() {
                @Override
                public boolean areItemsTheSame(@NonNull MeetingsViewState oldItem, @NonNull MeetingsViewState newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull MeetingsViewState oldItem, @NonNull MeetingsViewState newItem) {
                    return oldItem.equals(newItem);
                }

            };

}
