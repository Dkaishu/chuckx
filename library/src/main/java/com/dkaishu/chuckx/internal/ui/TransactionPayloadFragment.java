/*
 * Copyright (C) 2017 Jeff Gilfelt.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dkaishu.chuckx.internal.ui;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dkaishu.chuckx.R;
import com.dkaishu.chuckx.internal.data.HttpTransaction;
import com.dkaishu.chuckx.internal.support.ThreadUtils;

public class TransactionPayloadFragment extends Fragment implements TransactionFragment {

    public static final int TYPE_REQUEST = 0;
    public static final int TYPE_RESPONSE = 1;

    private static final String ARG_TYPE = "type";

    TextView headers;
    TextView body;

    private int type;
    private HttpTransaction transaction;

    public TransactionPayloadFragment() {
    }

    public static TransactionPayloadFragment newInstance(int type) {
        TransactionPayloadFragment fragment = new TransactionPayloadFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_TYPE, type);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(ARG_TYPE);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chuck_fragment_transaction_payload, container, false);
        headers = (TextView) view.findViewById(R.id.headers);
        body = (TextView) view.findViewById(R.id.body);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        populateUI();
    }

    @Override
    public void transactionUpdated(HttpTransaction transaction) {
        this.transaction = transaction;
        populateUI();
    }

    private void populateUI() {
        if (isAdded() && transaction != null) {
            switch (type) {
                case TYPE_REQUEST:
                    ThreadUtils.run(new Runnable() {
                        @Override
                        public void run() {
                            final String headersString = transaction.getRequestHeadersString(true);
                            final String requestBody = transaction.getFormattedRequestBody();
                            final Spanned spannedText = Html.fromHtml(headersString);
                            ThreadUtils.runOnUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    setText(headersString, spannedText, requestBody, transaction.requestBodyIsPlainText());
                                }
                            });
                        }
                    });
                    break;
                case TYPE_RESPONSE:
                    ThreadUtils.run(new Runnable() {
                        @Override
                        public void run() {
                            final String headersString = transaction.getResponseHeadersString(true);
                            final String responseBody = transaction.getFormattedResponseBody();
                            final Spanned spannedText = Html.fromHtml(headersString);
                            ThreadUtils.runOnUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    setText(headersString, spannedText, responseBody, transaction.responseBodyIsPlainText());
                                }
                            });
                        }
                    });
                    break;
            }
        }
    }

    private void setText(String headersString, Spanned spannedText, String bodyString, boolean isPlainText) {
        headers.setVisibility((TextUtils.isEmpty(headersString) ? View.GONE : View.VISIBLE));
        headers.setText(spannedText);
        if (!isPlainText) {
            body.setText(getString(R.string.chuck_body_omitted));
        } else {
            body.setText(bodyString);
        }
    }
}