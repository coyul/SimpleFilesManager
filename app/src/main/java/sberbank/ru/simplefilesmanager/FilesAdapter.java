package sberbank.ru.simplefilesmanager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Arrays;
import java.util.List;


public class FilesAdapter extends BaseAdapter {

    private final List<File> mFiles;
    private static final int BYTES_IN_KILOBYTES = 1024;

    public FilesAdapter(File[] files) {
        mFiles = Arrays.asList(files);
    }

    public int getCount() {
        return mFiles.size();
    }

    @Override
    public File getItem(int position) {
        return mFiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_list_item, parent, false);
            view.setTag(new ViewHolder(view));
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        File file = mFiles.get(position);

        holder.nameTextView.setText(file.getName());
        holder.sizeTextView.setText(parent.getResources().getString(R.string.file_size_format, getFileSizeInKb(file)));

        if (file.isDirectory()) {
            holder.iconImageView.setImageResource(R.drawable.icon_folder);
        } else {
            holder.iconImageView.setImageResource(R.drawable.icon_file);
        }
        return view;
    }


    private static class ViewHolder {

        public final TextView nameTextView;
        public final TextView sizeTextView;
        public final ImageView iconImageView;

        public ViewHolder(View itemView) {
            nameTextView = (TextView) itemView.findViewById(R.id.filename);
            sizeTextView = (TextView) itemView.findViewById(R.id.filesize);
            iconImageView = (ImageView) itemView.findViewById(R.id.fileicon);
        }
    }

    private float getFileSizeInKb(File file) {
        float size = 0;

        if (file.isFile() && file.exists()) {
            size = (float) file.length() / BYTES_IN_KILOBYTES;
        } else if (file.isDirectory()) {
            File directoryFiles[] = file.listFiles();
            if (directoryFiles != null) {
                for (File f : directoryFiles) {
                    size += (float) f.length() / BYTES_IN_KILOBYTES;
                }
            }
        }


        return size;
    }
}
