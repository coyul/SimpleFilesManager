package sberbank.ru.simplefilesmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

public class FilesActivity extends Activity {

    private ListView mFilesView;
    private FilesAdapter mFilesAdapter;
    private File mRootFile;

    private static final String PATH = "path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFilesView = (ListView) findViewById(R.id.file_list);

        if (getIntent().hasExtra(PATH)) {
            mRootFile = new File(getIntent().getStringExtra(PATH));
        } else {
            mRootFile = Environment.getRootDirectory();
        }

        final File[] fileList = mRootFile.listFiles();

        if (fileList != null && fileList.length > 0) {
            setUpAdapter(fileList);
        } else {
            Toast.makeText(this, R.string.directory_empty, Toast.LENGTH_SHORT).show();
        }

    }

    private void setUpAdapter(File[] fileList) {
        mFilesAdapter = new FilesAdapter(fileList);
        mFilesView.setAdapter(mFilesAdapter);

        mFilesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View parent, int position, long id) {
                File file = mFilesAdapter.getItem(position);
                if (file.isDirectory()) {
                    Intent intent = new Intent(parent.getContext(), FilesActivity.class);
                    intent.putExtra(PATH, file.getAbsolutePath());
                    startActivity(intent);
                } else {
                    Toast.makeText(parent.getContext(), R.string.not_a_directory, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
