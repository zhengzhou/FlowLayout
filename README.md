
FlowLayout
==========

a flowlayout which can be place one by one . 



### Horizontal flow layout, based on the FrameLayout transformation, can addview in layout or code. The Adapter is also used for filling the View.

![Screen](/S40528-092812.jpg)

See the code:

```java
            FlowFrameLayout flowLayout = (FlowFrameLayout) root.findViewById(R.id.flow_layout);
            Random r = new Random(13);
            for (int i = 0; i < 20; i++) {
                int len = r.nextInt(10);
                String s = generateString(r, characters, len);
                System.out.println(s);
                s = s + " " + i + " ";
                TextView text = new TextView(getActivity());
                text.setText(s);
                text.setTextSize(20);
                text.setBackgroundColor(Color.DKGRAY);
                flowLayout.setupChild(text, i);
            }
            flowLayout.requestLayout(); ```
            
            ##or use:
```java
   mFlowLayout = (FlowFrameLayout) findViewById(R.id.flow_list);
   mFlowLayout.setMaxLines(4);
   mFlowLayout.setOnItemClickListener(this);
   mFlowLayout.setAdapter(myAdapter); ```
   


