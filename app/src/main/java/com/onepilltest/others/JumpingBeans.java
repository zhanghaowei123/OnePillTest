package com.onepilltest.others;

    /*
     * Copyright 2014 Frakbot (Sebastiano Poggi and Francesco Pontillo)
     *
     *    Licensed under the Apache License, Version 2.0 (the "License");
     *    you may not use this file except in compliance with the License.
     *    You may obtain a copy of the License at
     *
     *        http://www.apache.org/licenses/LICENSE-2.0
     *
     *    Unless required by applicable law or agreed to in writing, software
     *    distributed under the License is distributed on an "AS IS" BASIS,
     *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     *    See the License for the specific language governing permissions and
     *    limitations under the License.
     */

  import android.support.annotation.NonNull;
  import android.text.SpannableStringBuilder;
  import android.text.Spanned;
  import android.text.TextUtils;
  import android.widget.TextView;
  import java.lang.ref.WeakReference;


    public final class JumpingBeans {

        /**
         * The default fraction of the whole animation time spent actually animating.
         * The rest of the range will be spent in "resting" state.
         * This the "duty cycle" of the jumping animation.
         */
        public static final float DEFAULT_ANIMATION_DUTY_CYCLE = 0.65f;

        /**
         * The default duration of a whole jumping animation loop, in milliseconds.
         */
        public static final int DEFAULT_LOOP_DURATION = 1300;   // ms

        public static final String ELLIPSIS_GLYPH = "…";
        public static final String THREE_DOTS_ELLIPSIS = "...";
        public static final int THREE_DOTS_ELLIPSIS_LENGTH = 3;

        private final JumpingBeansSpan[] jumpingBeans;
        private final WeakReference<TextView> textView;

        private JumpingBeans(@NonNull JumpingBeansSpan[] beans, @NonNull TextView textView) {
            this.jumpingBeans = beans;
            this.textView = new WeakReference<>(textView);
        }

        /**
         * Create an instance of the {@link net.frakbot.jumpingbeans.JumpingBeans.Builder}
         * applied to the provided {@code TextView}.
         *
         * @param textView The TextView to apply the JumpingBeans to
         * @return the {@link net.frakbot.jumpingbeans.JumpingBeans.Builder}
         */
        public static Builder with(@NonNull TextView textView) {
            return new Builder(textView);
        }

        /**
         * Stops the jumping animation and frees up the animations.
         */
        public void stopJumping() {
            for (JumpingBeansSpan bean : jumpingBeans) {
                if (bean != null) {
                    bean.teardown();
                }
            }

            cleanupSpansFrom(textView.get());
        }

        private static void cleanupSpansFrom(TextView tv) {
            if (tv != null) {
                CharSequence text = tv.getText();
                if (text instanceof Spanned) {
                    CharSequence cleanText = removeJumpingBeansSpansFrom((Spanned) text);
                    tv.setText(cleanText);
                }
            }
        }

        private static CharSequence removeJumpingBeansSpansFrom(Spanned text) {
            SpannableStringBuilder sbb = new SpannableStringBuilder(text.toString());
            Object[] spans = text.getSpans(0, text.length(), Object.class);
            for (Object span : spans) {
                if (!(span instanceof JumpingBeansSpan)) {
                    sbb.setSpan(span, text.getSpanStart(span),
                            text.getSpanEnd(span), text.getSpanFlags(span));
                }
            }
            return sbb;
        }

        private static CharSequence appendThreeDotsEllipsisTo(TextView textView) {
            CharSequence text = getTextSafe(textView);
            if (text.length() > 0 && endsWithEllipsisGlyph(text)) {
                text = text.subSequence(0, text.length() - 1);
            }

            if (!endsWithThreeEllipsisDots(text)) {
                text = new SpannableStringBuilder(text).append(THREE_DOTS_ELLIPSIS);  // Preserve spans in original text
            }
            return text;
        }

        private static CharSequence getTextSafe(TextView textView) {
            return !TextUtils.isEmpty(textView.getText()) ? textView.getText() : "";
        }

        private static boolean endsWithEllipsisGlyph(CharSequence text) {
            return TextUtils.equals(text.subSequence(text.length() - 1, text.length()), ELLIPSIS_GLYPH);
        }

        private static boolean endsWithThreeEllipsisDots(@NonNull CharSequence text) {
            if (text.length() < THREE_DOTS_ELLIPSIS_LENGTH) {
                // TODO we should try to normalize "invalid" ellipsis (e.g., ".." or "....")
                return false;
            }
            return TextUtils.equals(text.subSequence(text.length() - THREE_DOTS_ELLIPSIS_LENGTH, text.length()), THREE_DOTS_ELLIPSIS);
        }
        private static CharSequence ensureTextCanJump(int startPos, int endPos, CharSequence text) {
            if (text == null) {
                throw new NullPointerException("The textView text must not be null");
            }
            if (endPos < startPos) {
                throw new IllegalArgumentException("The start position must be smaller than the end position");
            }
            if (startPos < 0) {
                throw new IndexOutOfBoundsException("The start position must be non-negative");
            }
            if (endPos > text.length()) {
                throw new IndexOutOfBoundsException("The end position must be smaller than the text        length");
            }
            return text;
        }
        public static class Builder {
            private int startPos, endPos;
            private float animRange = DEFAULT_ANIMATION_DUTY_CYCLE;
            private int loopDuration = DEFAULT_LOOP_DURATION;
            private int waveCharDelay = -1;
            private CharSequence text;
            private TextView textView;
            private boolean wave;

            /*package*/ Builder(TextView textView) {
                this.textView = textView;
            }

            public Builder appendJumpingDots() {
                CharSequence text = appendThreeDotsEllipsisTo(textView);
                this.text = text;
                this.wave = true;
                this.startPos = text.length() - THREE_DOTS_ELLIPSIS_LENGTH;
                this.endPos = text.length();
                return this;
            }

            public Builder makeTextJump(int startPos, int endPos) {
                CharSequence text = textView.getText();
                ensureTextCanJump(startPos, endPos, text);

                this.text = text;
                this.wave = true;
                this.startPos = startPos;
                this.endPos = endPos;

                return this;
            }

            public Builder setAnimatedDutyCycle(float animatedRange) {
                if (animatedRange <= 0f || animatedRange > 1f) {
                    throw new IllegalArgumentException("The animated range must be in the (0, 1] range");
                }
                this.animRange = animatedRange;
                return this;
            }
            public Builder setLoopDuration(int loopDuration) {
                if (loopDuration < 1) {
                    throw new IllegalArgumentException("The loop duration must be bigger than zero");
                }
                this.loopDuration = loopDuration;
                return this;
            }
            public Builder setWavePerCharDelay(int waveCharOffset) {
                if (waveCharOffset < 0) {
                    throw new IllegalArgumentException("The wave char offset must be non-negative");
                }
                this.waveCharDelay = waveCharOffset;
                return this;
            }
            public Builder setIsWave(boolean wave) {
                this.wave = wave;
                return this;
            }
            public JumpingBeans build() {
                SpannableStringBuilder sbb = new SpannableStringBuilder(text);
                JumpingBeansSpan[] spans;
                if (wave) {
                    spans = getJumpingBeansSpans(sbb);
                } else {
                    spans = buildSingleSpan(sbb);
                }
                textView.setText(sbb);
                return new JumpingBeans(spans, textView);
            }

            private JumpingBeansSpan[] getJumpingBeansSpans(SpannableStringBuilder sbb) {
                JumpingBeansSpan[] spans;
                if (waveCharDelay == -1) {
                    waveCharDelay = loopDuration / (3 * (endPos - startPos));
                }
                spans = new JumpingBeansSpan[endPos - startPos];
                for (int pos = startPos; pos < endPos; pos++) {
                    JumpingBeansSpan jumpingBean =
                            new JumpingBeansSpan(textView, loopDuration, pos - startPos, waveCharDelay, animRange);
                    sbb.setSpan(jumpingBean, pos, pos + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spans[pos - startPos] = jumpingBean;
                }
                return spans;
            }

            private JumpingBeansSpan[] buildSingleSpan(SpannableStringBuilder sbb) {
                JumpingBeansSpan[] spans;
                spans = new JumpingBeansSpan[]{new JumpingBeansSpan(textView, loopDuration, 0, 0,  animRange)};
                sbb.setSpan(spans[0], startPos, endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return spans;
            }
        }
    }

