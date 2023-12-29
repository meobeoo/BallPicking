package com.example.bocbii;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText nEditText;
    private EditText kEditText;
    private EditText tEditText;
    private TextView resultTextView;
    private Button playButton;
    private EditText userMoveEditText;
    private Button userMoveButton;
    private Button help;

    private int n;
    private int k;
    private int t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nEditText = findViewById(R.id.n_edit_text);
        kEditText = findViewById(R.id.k_edit_text);
        tEditText = findViewById(R.id.t_edit_text);
        resultTextView = findViewById(R.id.result_text_view);
        playButton = findViewById(R.id.play_button);
        userMoveEditText = findViewById(R.id.user_input_edit_text);
        userMoveButton = findViewById(R.id.user_move_button);
        help = findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n = Integer.parseInt(nEditText.getText().toString());
                k = Integer.parseInt(kEditText.getText().toString());
                t = Integer.parseInt(tEditText.getText().toString());

                resultTextView.setText(""); // Xoá tất cả nội dung trong resultTextView

                playGame();
            }
        });

        userMoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userMove = Integer.parseInt(userMoveEditText.getText().toString());

                if (t == 1) {
                    if (userMove <= k) {
                        n = n - userMove;
                        resultTextView.append("Số viên còn lại: " + n + "\n");

                        t = 1 - t; // Chuyển lượt cho máy
                        if (n > 0) {
                            playGame();
                        } else {
                            if (t == 0) {
                                resultTextView.setText("Máy thua, bạn thắng, xin chúc mừng!\n" + resultTextView.getText());
                            } else {
                                resultTextView.setText("Bạn thua!\n" + resultTextView.getText());
                            }
                        }
                    } else {
                        resultTextView.setText("Số viên bốc không hợp lệ. Mời bạn bốc lại!\n" + resultTextView.getText());
                    }
                }
            }
        });
    }

    private void playGame() {
        if (t == 0) {
            int max = minValue(n - 1, k);
            int may_boc = 1;

            int so_bi_lon_nhat_co_the_boc = Math.min(k, n);

            for (int i = 2; i <= so_bi_lon_nhat_co_the_boc; i++) {
                int temp = minValue(n - i, k);
                if (max < temp) {
                    may_boc = i;
                    max = temp;
                    break;
                }
            }

            resultTextView.setText("Máy bốc " + may_boc + "\n" + resultTextView.getText());
            n = n - may_boc;
            resultTextView.setText("Số viên còn lại: " + n + "\n" + resultTextView.getText());

            t = 1 - t; // Chuyển lượt cho người chơi

            if (n <= 0) {
                if (t == 0) {
                    resultTextView.setText("Máy thua, bạn thắng, xin chúc mừng!\n" + resultTextView.getText());
                } else {
                    resultTextView.setText("Bạn thua!\n" + resultTextView.getText());
                }
            }
        }
    }

    private int maxValue(int m, int k) {
        if (m == 0) {
            return -1;
        } else {
            int maxV = -2;
            int so_bi_lon_nhat_co_the_boc = Math.min(k, m);

            for (int i = 1; i <= so_bi_lon_nhat_co_the_boc; i++) {
                int x = minValue(m - i, k);
                if (x > maxV) {
                    maxV = x;
                }
            }

            return maxV;
        }
    }

    private int minValue(int m, int k) {
        if (m == 0) {
            return 1;
        } else {
            int minV = 2;
            int so_bi_lon_nhat_co_the_boc = Math.min(k, m);

            for (int i = 1; i <= so_bi_lon_nhat_co_the_boc; i++) {
                int x = maxValue(m - i, k);
                if (x < minV) {
                    minV = x;
                }
            }

            return minV;
        }
    }
}
